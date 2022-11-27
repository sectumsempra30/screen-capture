package dev.hnatiuk.android.samples.screencapture.lib.internal.provider

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY
import android.hardware.display.DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC
import android.hardware.display.VirtualDisplay
import android.media.Image
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.view.Surface
import androidx.core.os.postDelayed
import dev.hnatiuk.android.samples.core.extensions.mediaProjectionManager
import dev.hnatiuk.android.samples.screencapture.lib.ScreenCaptureAccessData
import dev.hnatiuk.android.samples.screencapture.lib.internal.Utils
import dev.hnatiuk.android.samples.screencapture.lib.ScreenshotException
import dev.hnatiuk.android.samples.screencapture.lib.internal.ScreenshotSpecs
import dev.hnatiuk.android.samples.screencapture.lib.result.ScreenResult
import dev.hnatiuk.android.samples.screencapture.lib.result.Screenshot
import java.lang.ref.WeakReference

class MediaProjectionScreenshotProvider(
    context: Context,
    private val accessData: ScreenCaptureAccessData
) : ScreenshotProvider {

    private val contextReference = WeakReference(context)

    private val mainThreadHandler = Handler(Looper.getMainLooper())
    private var captureThread: HandlerThread? = null

    private var pendingResult: ScreenResult? = null

    private val projectionManager by lazy { context.mediaProjectionManager }

    override fun makeScreenshot(): ScreenResult {
        Utils.checkOnMainThread()
        val result = pendingResult
        if (result != null) {
            return result
        }
        val context = contextReference.get()
        if (context == null) {
            val exception = ScreenshotException.NoContextReference
            return ScreenResult.error(exception)
        }
        val screenshotSpec = ScreenshotSpecs(context)
        val newResult = ScreenResult(screenshotSpec)
        val projection = lastAccessData?.let(::getMediaProjection)
        pendingResult = newResult
        if (projection == null) {
            onActivityResult(accessData.resultCode, accessData.data)
        } else {
            captureInBackground(projection, screenshotSpec)
        }
        return newResult
    }

    private fun onActivityResult(resultCode: Int, data: Intent) {
        val screenshotSpec = pendingResult?.spec ?: return
        val accessData = ScreenCaptureAccessData(resultCode, data)
        val mediaProjection = getMediaProjection(accessData)
        if (mediaProjection != null) {
            captureInBackground(mediaProjection, screenshotSpec, DIALOG_CLOSED_DELAY_MS)
            lastAccessData = accessData
        } else {
            val exception = ScreenshotException.FailedToCreateMediaProjection
            onScreenshotCaptureFailed(exception)
        }
    }

    private fun captureInBackground(
        projection: MediaProjection,
        screenshotSpecs: ScreenshotSpecs,
        delayMs: Long = 0L
    ) {
        val captureThread = startCaptureThread()
        val captureThreadHandler = Handler(captureThread.looper)
        captureThreadHandler.postDelayed(delayMs) {
            doCapture(projection, screenshotSpecs, captureThreadHandler)
        }
    }

    private fun doCapture(projection: MediaProjection, spec: ScreenshotSpecs, handler: Handler) {
        var imageReader: ImageReader? = null
        var virtualDisplay: VirtualDisplay? = null
        try {
            imageReader = createImageReader(projection, spec, handler)
            virtualDisplay = createVirtualDisplay(projection, imageReader.surface, spec, handler)
            val callback = ReleaseOnStopCallback(projection, imageReader, virtualDisplay)
            projection.registerCallback(callback, handler)
        } catch (e: Exception) {
            Utils.releaseSafely(virtualDisplay)
            Utils.closeSafely(imageReader)
            Utils.stopSafely(projection)
            onScreenshotCaptureFailed(ScreenshotException.ScreenTakeException(e))
        }
    }

    private fun createImageReader(
        projection: MediaProjection,
        spec: ScreenshotSpecs,
        callbackHandler: Handler
    ): ImageReader {
        //Lint forces to use ImageFormat (API 23+) instead of PixelFormat, even though ImageReader docs say that PixelFormat is supported
        //noinspection WrongConstant
        val imageReader = ImageReader.newInstance(spec.width, spec.height, PixelFormat.RGBA_8888, 2)
        imageReader.setOnImageAvailableListener(
            ImageAvailableListener(
                projection,
                spec.width,
                spec.height
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

    private fun onScreenshotCaptured(bitmap: Bitmap) {
        mainThreadHandler.post {
            val screenshot = Screenshot(bitmap)
            pendingResult?.onSuccess(screenshot)
            pendingResult = null
            stopCaptureThread()
        }
    }

    private fun onScreenshotCaptureFailed(cause: ScreenshotException) {
        mainThreadHandler.post {
            pendingResult?.onError(cause)
            pendingResult = null
            stopCaptureThread()
        }
    }

    private fun startCaptureThread(): HandlerThread {
        Utils.checkOnMainThread()
        var thread = captureThread
        if (thread == null) {
            thread = HandlerThread(CAPTURE_THREAD_NAME)
            thread.start()
            captureThread = thread
        }
        return thread
    }

    private fun stopCaptureThread() {
        captureThread?.let {
            Utils.interruptSafely(it)
            captureThread = null
        }
    }

    private fun getMediaProjection(accessData: ScreenCaptureAccessData): MediaProjection? {
        return projectionManager.getMediaProjection(accessData.resultCode, accessData.data)
    }

    private inner class ImageAvailableListener(
        private val projection: MediaProjection,
        private val width: Int,
        private val height: Int
    ) : ImageReader.OnImageAvailableListener {
        private var processed = false

        override fun onImageAvailable(reader: ImageReader) {
            if (processed) return
            processed = true
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
                    onScreenshotCaptured(bitmap)
                } else {
                    val exception = ScreenshotException.FailedToAcquireImage
                    onScreenshotCaptureFailed(exception)
                }
            } catch (e: Exception) {
                onScreenshotCaptureFailed(ScreenshotException.ScreenTakeException(e))
                bitmap?.recycle()
            } finally {
                Utils.closeSafely(image)
                Utils.stopSafely(projection)
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

        private const val DIALOG_CLOSED_DELAY_MS = 150L
        private const val VIRTUAL_DISPLAY_FLAGS =
            VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY or VIRTUAL_DISPLAY_FLAG_PUBLIC

        private const val CAPTURE_THREAD_NAME = "ScreenCaptureThread"

        private var lastAccessData: ScreenCaptureAccessData? = null
    }
}