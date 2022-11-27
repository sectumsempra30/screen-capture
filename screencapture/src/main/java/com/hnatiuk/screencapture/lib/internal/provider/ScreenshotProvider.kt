package dev.hnatiuk.screencapture.lib.internal.provider

import dev.hnatiuk.screencapture.lib.result.ScreenshotResult

interface ScreenshotProvider {

    fun makeScreenshot(): ScreenshotResult
}