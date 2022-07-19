package com.hnatiuk.core.extensions

import android.app.NotificationManager
import android.content.Context
import android.media.projection.MediaProjectionManager
import android.view.WindowManager
import java.lang.IllegalStateException

val Context.windowManager: WindowManager
    get() = getService(Context.WINDOW_SERVICE)

val Context.notificationManager: NotificationManager
    get() = getService(Context.NOTIFICATION_SERVICE)

val Context.mediaProjectionManager: MediaProjectionManager
    get() = getService(Context.MEDIA_PROJECTION_SERVICE)

@Suppress("UNCHECKED_CAST")
private fun <Type> Context.getService(name: String): Type {
    return getSystemService(name) as? Type ?:
    throw IllegalStateException("Cannot get $name service")
}