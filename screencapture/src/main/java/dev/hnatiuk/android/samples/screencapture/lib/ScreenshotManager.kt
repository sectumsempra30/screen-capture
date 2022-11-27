package dev.hnatiuk.android.samples.screencapture.lib

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import dev.hnatiuk.android.samples.core.utils.SimpleServiceConnection
import dev.hnatiuk.android.samples.core.utils.isAtLeastAndroidQ
import dev.hnatiuk.android.samples.core.utils.isAtLeastOreo
import dev.hnatiuk.android.samples.screencapture.lib.internal.ScreenCaptureService
import dev.hnatiuk.android.samples.screencapture.lib.internal.ScreenCaptureService.*
import dev.hnatiuk.android.samples.screencapture.lib.result.Screenshot
import dev.hnatiuk.android.samples.screencapture.lib_v2.MediaProjectionScreenshotProviderV2
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import java.lang.ref.WeakReference

class ScreenshotManager constructor(
    private val context: Context
) {

    private var accessData: ScreenCaptureAccessData? = null

    private val provider by lazy {
        val accessData = accessData ?: throw ScreenshotException.NoMediaProjectionPermission
        MediaProjectionScreenshotProviderV2(context).apply {
            init(accessData)
        }
    }

    private var screenshotServiceConnection: ServiceConnection? = null
    private var serviceBinder: WeakReference<ScreenCaptureService.ScreenCaptureBinder>? = null

    fun onPermissionGranted(accessData: ScreenCaptureAccessData) {
        this.accessData = accessData
    }

    fun isPermissionGranted(): Boolean {
        return accessData != null
    }

    fun makeScreenshot(forId: String?): Single<Screenshot> = when {
        isPermissionGranted() && isAtLeastAndroidQ() -> provideScreenshotWithService(forId)
        isPermissionGranted() -> provideScreenshotWithoutService(forId)
        else -> Single.error(ScreenshotException.NoMediaProjectionPermission)
    }.subscribeOn(AndroidSchedulers.mainThread())

    fun onDestroy() = with(context) {
        screenshotServiceConnection?.let { connection ->
            unbindService(connection)
            toggleServiceWithIntent(ScreenCaptureService.stopIntent(this))
        }
    }

    private fun provideScreenshotWithoutService(forId: String?): Single<Screenshot> {
        return provider.makeScreenshot(forId)
    }

    private fun provideScreenshotWithService(forId: String?): Single<Screenshot> {
        return when (val service = serviceBinder?.get()) {
            null -> Single.create {
                val screenshotServiceConnection = object : SimpleServiceConnection<ScreenCaptureBinder>() {
                    override fun onServiceConnected(service: ScreenCaptureBinder) {
                        serviceBinder = WeakReference(service)
                        it.onSuccess(service)
                    }
                }
                this.screenshotServiceConnection = screenshotServiceConnection
                startScreenCaptureService(screenshotServiceConnection)
            }
            else -> Single.just(service)
        }.flatMap {
            it.makeScreenshot(forId)
        }
    }

    private fun startScreenCaptureService(connection: SimpleServiceConnection<ScreenCaptureService.ScreenCaptureBinder>) =
        with(context) {
            val accessData = accessData ?: throw ScreenshotException.NoMediaProjectionPermission
            val intent = ScreenCaptureService.newIntent(this, accessData)
            toggleServiceWithIntent(intent)
            bindService(Intent(this, ScreenCaptureService::class.java), connection, Context.BIND_AUTO_CREATE)
        }

    private fun toggleServiceWithIntent(intent: Intent) = with(context) {
        if (isAtLeastOreo()) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }
}