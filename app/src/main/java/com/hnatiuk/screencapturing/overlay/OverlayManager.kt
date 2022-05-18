package com.hnatiuk.screencapturing.overlay

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.PixelFormat
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.view.WindowManager.*

class OverlayManager(private val context: Context) {

    private val windowManager by lazy {
        context.getSystemService(WINDOW_SERVICE) as WindowManager
    }

    fun start(view: View) {
        val params = getParams()
        view.setOnTouchListener(OverlayTouchListener(view, windowManager, params))
        windowManager.addView(view, params)
    }

    private fun getParams(): LayoutParams {
        return LayoutParams().apply {
            width = LayoutParams.WRAP_CONTENT
            height = LayoutParams.WRAP_CONTENT
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                LayoutParams.TYPE_PHONE
            }
            flags = LayoutParams.FLAG_NOT_FOCUSABLE or LayoutParams.FLAG_SECURE
            format = PixelFormat.RGBA_8888
        }
    }
}