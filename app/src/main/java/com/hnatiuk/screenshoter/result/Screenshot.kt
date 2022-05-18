package com.hnatiuk.screenshoter.result

import android.graphics.Bitmap

sealed class Screenshot {

    class ScreenshotBitmap(val bitmap: Bitmap) : Screenshot()
}