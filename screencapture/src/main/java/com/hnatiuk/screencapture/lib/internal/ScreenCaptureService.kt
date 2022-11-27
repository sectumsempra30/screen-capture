package dev.hnatiuk.screencapture.lib.internal

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MIN
import dev.hnatiuk.android.samples.core.BuildConfig
import dev.hnatiuk.android.samples.core.extensions.notificationManager
import dev.hnatiuk.android.samples.core.extensions.parcelable
import dev.hnatiuk.android.samples.core.utils.isAtLeastOreo
import dev.hnatiuk.screencapture.lib.ScreenCaptureAccessData
import dev.hnatiuk.screencapture.lib.result.ScreenshotResult
import dev.hnatiuk.screencapture.lib_v2.MediaProjectionScreenshotProviderV2

class ScreenCaptureService : Service() {

    private var screenCaptureSubscription: ScreenshotResult.Subscription? = null
    private lateinit var screenshotProvider: MediaProjectionScreenshotProviderV2

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when {
            intent.action == ACTION_STOP -> stopForegroundService()
            isAtLeastOreo() -> {
                startForeground(SERVICE_ID, createForegroundNotification())
                initScreenshotProvider(intent)
            }
        }

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        return ScreenCaptureBinder()
    }

    override fun onDestroy() {
        super.onDestroy()
        screenCaptureSubscription?.dispose()
    }

    private fun initScreenshotProvider(intent: Intent) {
        val accessData = intent.parcelable<ScreenCaptureAccessData>(ACCESS_DATA_EXTRA_KEY)
            ?: throw IllegalArgumentException("ProjectionAccessData cannot be null")

        screenshotProvider = MediaProjectionScreenshotProviderV2(this).apply {
            init(accessData)
        }
    }

    private fun stopForegroundService() {
        stopSelf()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_DETACH)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createForegroundNotification(): Notification {
        val channelId = createNotificationChannel()
            .takeIf { isAtLeastOreo() }
            .orEmpty()

        return NotificationCompat.Builder(this, channelId)
            //.setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(PRIORITY_MIN)
            .setSilent(true)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setContentTitle("Taking screenshot..")
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): String {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_NONE
        ).apply {
            lightColor = Color.BLUE
            lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        }
        applicationContext.notificationManager.createNotificationChannel(channel)
        return NOTIFICATION_CHANNEL_ID
    }

    companion object {

        private const val SERVICE_ID = 123
        private const val NOTIFICATION_CHANNEL_ID = "SCREEN_CAPTURE_CHANNEL_ID"
        private const val NOTIFICATION_CHANNEL_NAME = "SCREEN_CAPTURE_CHANNEL_NAME"

        private const val ACTION_STOP = "${BuildConfig.LIBRARY_PACKAGE_NAME}.stop"

        private const val ACCESS_DATA_EXTRA_KEY = "ACCESS_DATA_EXTRA_KEY"

        fun stopIntent(context: Context) = Intent(context, ScreenCaptureService::class.java).apply {
            action = ACTION_STOP
        }

        fun newIntent(context: Context, accessData: ScreenCaptureAccessData) =
            Intent(context, ScreenCaptureService::class.java).apply {
                putExtra(ACCESS_DATA_EXTRA_KEY, accessData)
            }
    }

    inner class ScreenCaptureBinder : Binder() {

        fun makeScreenshot(forId: String?) = screenshotProvider.makeScreenshot(forId)
    }
}