package dev.hnatiuk.screencapture.lib.internal

import android.content.Context
import android.graphics.Point
import android.view.Display
import android.view.WindowInsets.Type.displayCutout
import android.view.WindowInsets.Type.navigationBars
import dev.hnatiuk.android.samples.core.extensions.windowManager
import dev.hnatiuk.android.samples.core.utils.isAtLeastAndroidR

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
            val insets =
                windowInsets.getInsetsIgnoringVisibility(navigationBars() or displayCutout())
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