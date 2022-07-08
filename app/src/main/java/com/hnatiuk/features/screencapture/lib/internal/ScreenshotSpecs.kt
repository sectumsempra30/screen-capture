package com.hnatiuk.features.screencapture.lib.internal

import android.content.Context
import android.graphics.Point
import android.view.Display
import android.view.WindowInsets.Type.*
import com.hnatiuk.core.isAtLeastAndroidR
import com.hnatiuk.core.windowManager

class ScreenshotSpecs(context: Context) {

    val width: Int
    val height: Int
    val densityDpi: Int

    init {
        val displaySize = getDisplaySize(context)
        width = displaySize.x
        height = displaySize.y
        densityDpi = context.resources.displayMetrics.densityDpi
    }

    @Suppress("DEPRECATION")
    private fun getDisplaySize(context: Context): Point {
        return if (isAtLeastAndroidR()) {
            val windowMetrics = context.windowManager.currentWindowMetrics
            val windowInsets = windowMetrics.windowInsets
            val insets = windowInsets.getInsetsIgnoringVisibility(navigationBars() or displayCutout())
            val bounds = windowMetrics.bounds

            Point(
                bounds.width() - (insets.right + insets.left),
                bounds.height() - (insets.top + insets.bottom)
            )
        } else {
            Point().apply {
                getDisplay(context).getSize(this)
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun getDisplay(context: Context): Display {
        return if (isAtLeastAndroidR()) {
            context.display
                ?: throw IllegalArgumentException("Such context is not associated with any display")
        } else {
            context.windowManager.defaultDisplay
        }
    }
}