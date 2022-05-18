package com.hnatiuk.screencapturing.overlay

import android.graphics.Point
import android.graphics.PointF
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager

class OverlayTouchListener(
    private val view: View,
    private val windowManager: WindowManager,
    private val params: WindowManager.LayoutParams
) : View.OnTouchListener {

    private var dragStartRaw = PointF()
    private var dragStart = Point()

    @Suppress("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                dragStart.x = params.x
                dragStart.y = params.y
                dragStartRaw.x = event.rawX
                dragStartRaw.y = event.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                val xDir = if (params.gravity and Gravity.END == Gravity.END) -1 else 1
                val yDir = if (params.gravity and Gravity.BOTTOM == Gravity.BOTTOM) -1 else 1
                params.x = dragStart.x + ((event.rawX - dragStartRaw.x) * xDir).toInt()
                params.y = dragStart.y + ((event.rawY - dragStartRaw.y) * yDir).toInt()
                windowManager.updateViewLayout(view, params)
            }
        }
        return false
    }
}