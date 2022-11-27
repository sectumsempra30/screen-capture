package dev.hnatiuk.screencapture.lib.result

import android.graphics.Bitmap

open class Screenshot(val bitmap: Bitmap)

class ScreenshotId(
    bitmap: Bitmap,
    val id: String?
) : Screenshot(bitmap)