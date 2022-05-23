package com.hnatiuk.screencapture.internal

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
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MIN
import com.hnatiuk.core.isAtLeastOreo
import com.hnatiuk.core.notificationManager
import com.hnatiuk.presentation.BuildConfig
import com.hnatiuk.presentation.R
import com.hnatiuk.screencapture.ScreenCaptureAccessData
import com.hnatiuk.screencapture.internal.provider.MediaProjectionScreenshotProvider
import com.hnatiuk.screencapture.result.Screenshot
import com.hnatiuk.screencapture.result.ScreenshotResult

class ScreenCaptureService : Service() {

    private var screenCaptureListener: OnScreenCaptureListener? = null

    private var screenCaptureSubscription: ScreenshotResult.Subscription? = null
    private lateinit var screenshotProvider: MediaProjectionScreenshotProvider

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when {
            intent.action == ACTION_STOP -> stopForegroundService()
            isAtLeastOreo() -> {
                startForeground(SERVICE_ID, createForegroundNotification())
                initScreenshotProvider(intent)
                startScreenCapture()
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
        val accessData = intent.getParcelableExtra<ScreenCaptureAccessData>(ACCESS_DATA_EXTRA_KEY)
            ?: throw IllegalArgumentException("ProjectionAccessData cannot be null")

        screenshotProvider = MediaProjectionScreenshotProvider(applicationContext, accessData)
    }

    private fun startScreenCapture() {
        screenCaptureSubscription = screenshotProvider.makeScreenshot()
            .observe(
                { result ->
                    Log.i("onScreenCaptured", "success $result")
                    screenCaptureListener?.onScreenCapture(result)
                    stopForegroundService()

                },
                { error ->
                    Log.i("onScreenCaptured", "error $error")
                    screenCaptureListener?.onError(error)
                }
            )
    }

    private fun stopForegroundService() {
        stopSelf()
        stopForeground(true)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createForegroundNotification(): Notification {
        val channelId = createNotificationChannel()
            .takeIf { isAtLeastOreo() }
            .orEmpty()

        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
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

        private const val ACTION_STOP = "${BuildConfig.APPLICATION_ID}.stop"

        private const val ACCESS_DATA_EXTRA_KEY = "ACCESS_DATA_EXTRA_KEY"

        fun stopIntent(context: Context) = Intent(context, ScreenCaptureService::class.java).apply {
            action = ACTION_STOP
        }

        fun newIntent(context: Context, accessData: ScreenCaptureAccessData) = Intent(context, ScreenCaptureService::class.java).apply {
            putExtra(ACCESS_DATA_EXTRA_KEY, accessData)
        }
    }

    interface OnScreenCaptureListener {

        fun onScreenCapture(screenshot: Screenshot)

        fun onError(error: Throwable)
    }

    inner class ScreenCaptureBinder : Binder() {

        fun setOnScreenCaptureListener(listener: OnScreenCaptureListener) {
            this@ScreenCaptureService.screenCaptureListener = listener
        }
    }
}