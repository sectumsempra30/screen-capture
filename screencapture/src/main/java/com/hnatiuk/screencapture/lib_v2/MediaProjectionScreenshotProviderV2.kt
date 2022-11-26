package com.hnatiuk.screencapture.lib_v2;

import android.content.Context
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager.*
import android.hardware.display.VirtualDisplay
import android.media.Image
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.Surface
import com.hnatiuk.core.extensions.mediaProjectionManager
import com.hnatiuk.screencapture.lib.ScreenCaptureAccessData
import com.hnatiuk.screencapture.lib.ScreenshotException
import com.hnatiuk.screencapture.lib.internal.ScreenshotSpecs
import com.hnatiuk.screencapture.lib.internal.Utils
import com.hnatiuk.screencapture.lib.result.Screenshot
import com.hnatiuk.screencapture.lib.result.ScreenshotId
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.PublishSubject
import java.lang.ref.WeakReference

class MediaProjectionScreenshotProviderV2(context: Context) {

    val screenshotPublisher = PublishSubject.create<Result<ScreenshotId>>()
    var isCaptureStarted: Boolean = false

    private var isInit: Boolean = false

    private val contextRef = WeakReference(context)
    private val context: Context
        get() = contextRef.get() ?: throw ScreenshotException.NoContextReference

    private lateinit var specs: ScreenshotSpecs
    private lateinit var projectionManager: MediaProjectionManager

    private lateinit var captureThread: HandlerThread
    private lateinit var captureThreadHandler: Handler

    private val vars = object {

        lateinit var imageReader: ImageReader
        lateinit var virtualDisplay: VirtualDisplay
        var id: String? = null
    }

    fun makeScreenshot(forId: String?): Single<Screenshot> {
        return screenshotPublisher
            .doOnSubscribe { toggleCapture(enable = true, forId = forId) }
            .doOnNext {
                it.exceptionOrNull()?.let { error ->
                    throw error
                }
            }
            .filter { it.isSuccess && it.getOrNull()?.id == forId }
            .map { it.getOrNull() as Screenshot }
            .firstOrError()
    }

    fun init(accessData: ScreenCaptureAccessData) {
        if (isInit.not()) {
            specs = ScreenshotSpecs(context)
            projectionManager = context.mediaProjectionManager
            isInit = true
        }

        if (isCaptureStarted) {
            return
        }

        val projection = getMediaProjection(accessData)
        if (projection != null) {
            lastAccessData = accessData
            startCaptureThread(projection)
        } else {
            screenshotPublisher.onNext(Result.failure(ScreenshotException.FailedToCreateMediaProjection))
        }
    }

    fun toggleCapture(enable: Boolean, forId: String? = null) {
        if (isCaptureStarted.not()) {
            return
        }
        captureThreadHandler.post {
            vars.id = if (enable) forId else null
            vars.virtualDisplay.surface = if (enable) vars.imageReader.surface else null
        }
    }

    private fun startCaptureThread(projection: MediaProjection) {
        if (isCaptureStarted) {
            throw ScreenshotException.CaptureAlreadyStartedException
        }
        isCaptureStarted = true
        captureThread = HandlerThread(CAPTURE_THREAD_NAME)
        captureThread.start()
        captureThreadHandler = Handler(captureThread.looper)
        captureThreadHandler.post {
            doCapture(projection)
        }
    }

    private fun doCapture(projection: MediaProjection) {
        val handler = captureThreadHandler

        var imageReader: ImageReader? = null
        var virtualDisplay: VirtualDisplay? = null
        try {
            imageReader = createImageReader(specs, handler)
            virtualDisplay = createVirtualDisplay(projection, imageReader.surface, specs, handler)

            vars.imageReader = imageReader
            vars.virtualDisplay = virtualDisplay

            val releaseOnStopCallback = ReleaseOnStopCallback(projection, imageReader, virtualDisplay)
            projection.registerCallback(releaseOnStopCallback, handler)
        } catch (e: Exception) {
            Utils.releaseSafely(virtualDisplay)
            Utils.closeSafely(imageReader)
            Utils.stopSafely(projection)
            screenshotPublisher.onNext(Result.failure(e))
        }
    }

    private fun createImageReader(specs: ScreenshotSpecs, callbackHandler: Handler): ImageReader {
        //Lint forces to use ImageFormat (API 23+) instead of PixelFormat, even though ImageReader docs say that PixelFormat is supported
        //noinspection WrongConstant
        val imageReader = ImageReader.newInstance(specs.width, specs.height, PixelFormat.RGBA_8888, 2)
        imageReader.setOnImageAvailableListener(
            ImageAvailableListener(
                specs.width,
                specs.height
            ), callbackHandler
        )
        return imageReader
    }

    private fun createVirtualDisplay(
        projection: MediaProjection,
        surface: Surface,
        spec: ScreenshotSpecs,
        callbackHandler: Handler
    ): VirtualDisplay {
        return projection.createVirtualDisplay(
            CAPTURE_THREAD_NAME, spec.width, spec.height, spec.densityDpi,
            VIRTUAL_DISPLAY_FLAGS, surface, null,
            callbackHandler
        )
    }

    private fun getMediaProjection(accessData: ScreenCaptureAccessData): MediaProjection? {
        return projectionManager.getMediaProjection(accessData.resultCode, accessData.data)
    }

    private inner class ImageAvailableListener constructor(
        private val width: Int,
        private val height: Int
    ) : ImageReader.OnImageAvailableListener {

        override fun onImageAvailable(reader: ImageReader) {
            var image: Image? = null
            var bitmap: Bitmap? = null
            try {
                image = reader.acquireLatestImage()
                if (image != null) {
                    val planes = image.planes
                    val buffer = planes[0].buffer
                    val pixelStride = planes[0].pixelStride
                    val rowStride = planes[0].rowStride
                    val rowPadding = rowStride - pixelStride * width

                    bitmap = Bitmap.createBitmap(
                        width + rowPadding / pixelStride,
                        height,
                        Bitmap.Config.ARGB_8888
                    )
                    bitmap?.copyPixelsFromBuffer(buffer)

                    Log.i("checkSc", vars.id.orEmpty())
                    screenshotPublisher.onNext(Result.success(ScreenshotId(bitmap, vars.id)))
                    toggleCapture(enable = false)
                } else {
                    screenshotPublisher.onNext(Result.failure(ScreenshotException.FailedToAcquireImage))
                }
            } catch (error: Exception) {
                screenshotPublisher.onNext(Result.failure(error))
                bitmap?.recycle()
            } finally {
                Utils.closeSafely(image)
            }
        }
    }

    private class ReleaseOnStopCallback constructor(
        private val projection: MediaProjection,
        private val imageReader: ImageReader,
        private val virtualDisplay: VirtualDisplay
    ) : MediaProjection.Callback() {

        override fun onStop() {
            Utils.releaseSafely(virtualDisplay)
            Utils.closeSafely(imageReader)
            projection.unregisterCallback(this)
        }
    }

    companion object {

        private const val VIRTUAL_DISPLAY_FLAGS = VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY or VIRTUAL_DISPLAY_FLAG_PUBLIC

        private const val CAPTURE_THREAD_NAME = "ScreenCaptureThread"

        private var lastAccessData: ScreenCaptureAccessData? = null
    }
}