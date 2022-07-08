package com.hnatiuk.features.screencapture.lib.internal.provider

import com.hnatiuk.features.screencapture.lib.result.ScreenshotResult

interface ScreenshotProvider {

    fun makeScreenshot(): ScreenshotResult
}