package com.hnatiuk.screencapture

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.annotation.RequiresApi
import com.hnatiuk.core.SimpleServiceConnection
import com.hnatiuk.core.isAtLeastAndroidQ
import com.hnatiuk.core.isAtLeastOreo
import com.hnatiuk.screencapture.internal.ScreenCaptureService
import com.hnatiuk.screencapture.internal.provider.MediaProjectionScreenshotProvider
import com.hnatiuk.screencapture.result.Screenshot
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
        return if (isAtLeastAndroidQ()) {
            provideScreenCapture()
        } else {
            provideScreenCaptureWithoutService()
        }.subscribeOn(AndroidSchedulers.mainThread())
    }

    fun onDestroy() = with(context) {
        screenshotServiceConnection?.let { connection ->
            unbindService(connection)
            toggleServiceWithIntent(ScreenCaptureService.stopIntent(this))
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
}