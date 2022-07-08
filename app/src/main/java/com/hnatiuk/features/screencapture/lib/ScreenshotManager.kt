package com.hnatiuk.features.screencapture.lib

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.annotation.RequiresApi
import com.hnatiuk.core.SimpleServiceConnection
import com.hnatiuk.core.isAtLeastAndroidQ
import com.hnatiuk.core.isAtLeastOreo
import com.hnatiuk.features.screencapture.lib.internal.ScreenCaptureService
import com.hnatiuk.features.screencapture.lib.internal.provider.MediaProjectionScreenshotProvider
import com.hnatiuk.features.screencapture.lib.result.Screenshot
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import java.lang.ref.WeakReference
import javax.inject.Inject

class ScreenshotManager @Inject constructor() {

    private lateinit var contextReference: WeakReference<Context>
    private lateinit var accessData: ScreenCaptureAccessData

    private val provider by lazy {
        MediaProjectionScreenshotProvider(context, accessData)
    }

    private val context: Context
        get() = contextReference.get() ?: throw ScreenshotException.NoContextReference

    private var screenshotServiceConnection: ServiceConnection? = null

    fun init(context: Context, accessData: ScreenCaptureAccessData) {
        contextReference = WeakReference(context)
        this.accessData = accessData
    }

    @SuppressLint("NewApi")
    fun makeScreenshot(): Single<Screenshot> {
        return if (isInitialized()) {
            if (isAtLeastAndroidQ()) {
                provideScreenCapture()
            } else {
                provideScreenCaptureWithoutService()
            }.subscribeOn(AndroidSchedulers.mainThread())
        } else {
            Single.error(ScreenshotException.NoMediaProjectionPermission)
        }
    }

    fun onDestroy() {
        if (isInitialized()) {
            screenshotServiceConnection?.let { connection ->
                context.unbindService(connection)
                toggleServiceWithIntent(ScreenCaptureService.stopIntent(context))
            }
        }
    }

    private fun provideScreenCaptureWithoutService(): Single<Screenshot> {
        return Single.create<Screenshot> { emitter ->
            val result = provider.makeScreenshot()
            result.observe(emitter::onSuccess, emitter::onError)
        }.subscribeOn(AndroidSchedulers.mainThread())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun provideScreenCapture(): Single<Screenshot> {
        return Single.create { emitter ->
            val screenshotServiceConnection = object : SimpleServiceConnection<ScreenCaptureService.ScreenCaptureBinder>() {
                override fun onServiceConnected(service: ScreenCaptureService.ScreenCaptureBinder) {
                    service.setOnScreenCaptureListener(object : ScreenCaptureService.OnScreenCaptureListener {
                        override fun onScreenCapture(screenshot: Screenshot) {
                            emitter.onSuccess(screenshot)
                        }

                        override fun onError(error: Throwable) {
                            emitter.onError(error)
                        }
                    })
                }
            }
            this.screenshotServiceConnection = screenshotServiceConnection

            with(context) {
                val intent = ScreenCaptureService.newIntent(this, accessData)
                toggleServiceWithIntent(intent)
                bindService(Intent(this, ScreenCaptureService::class.java), screenshotServiceConnection, Context.BIND_AUTO_CREATE)
            }
        }
    }

    private fun toggleServiceWithIntent(intent: Intent) = with(context) {
        if (isAtLeastOreo()) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    private fun isInitialized(): Boolean {
        return ::contextReference.isInitialized && ::accessData.isInitialized
    }
}