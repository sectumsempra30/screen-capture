package com.hnatiuk.screencapture.internal.provider

import com.hnatiuk.screencapture.result.ScreenshotResult

interface ScreenshotProvider {

    fun makeScreenshot(): ScreenshotResult
}