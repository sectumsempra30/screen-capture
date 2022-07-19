package com.hnatiuk.screencapture.lib.result

interface ScreenshotResult {

    fun observe(onSuccess: (Screenshot) -> Unit, onError: (Throwable) -> Unit): Subscription

    interface Subscription {
        fun dispose()
    }
}