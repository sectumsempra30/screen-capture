package com.hnatiuk.core

import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Context.*
import android.media.projection.MediaProjectionManager
import android.view.WindowManager
import android.widget.Toast
import java.lang.IllegalStateException

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

val Context.windowManager: WindowManager
    get() = getService(WINDOW_SERVICE)

val Context.notificationManager: NotificationManager
    get() = getService(NOTIFICATION_SERVICE)

val Context.mediaProjectionManager: MediaProjectionManager
    get() = getService(MEDIA_PROJECTION_SERVICE)

@Suppress("UNCHECKED_CAST")
private fun <Type> Context.getService(name: String): Type {
    return getSystemService(name) as? Type ?:
        throw IllegalStateException("Cannot get $name service")
}