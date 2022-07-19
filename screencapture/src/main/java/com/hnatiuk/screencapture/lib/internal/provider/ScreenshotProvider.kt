package com.hnatiuk.screencapture.lib.internal.provider

import com.hnatiuk.screencapture.lib.result.ScreenshotResult

interface ScreenshotProvider {

    fun makeScreenshot(): ScreenshotResult
}