package dev.hnatiuk.android.samples.screencapture.lib.internal.provider

import dev.hnatiuk.android.samples.screencapture.lib.result.ScreenshotResult

interface ScreenshotProvider {

    fun makeScreenshot(): ScreenshotResult
}