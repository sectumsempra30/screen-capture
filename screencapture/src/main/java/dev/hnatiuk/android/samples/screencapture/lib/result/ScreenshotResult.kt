package dev.hnatiuk.android.samples.screencapture.lib.result

interface ScreenshotResult {

    fun observe(onSuccess: (Screenshot) -> Unit, onError: (Throwable) -> Unit): Subscription

    interface Subscription {
        fun dispose()
    }
}