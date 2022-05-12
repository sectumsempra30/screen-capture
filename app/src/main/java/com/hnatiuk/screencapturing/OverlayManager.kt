package com.hnatiuk.screencapturing

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.PixelFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.annotation.LayoutRes

class OverlayManager(private val context: Context) {

    private val windowManager by lazy {
        context.getSystemService(WINDOW_SERVICE) as WindowManager
    }

    fun start(@LayoutRes viewRes: Int) {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(viewRes, null)
        start(view)
    }

    fun start(view: View) {
        val params = getParams()
        windowManager.addView(view, params)
    }

    private fun getParams(): WindowManager.LayoutParams {
        return WindowManager.LayoutParams().apply {
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                @Suppress("DEPRECATION")
                WindowManager.LayoutParams.TYPE_PHONE
            }
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            format = PixelFormat.RGBA_8888
        }
    }
}