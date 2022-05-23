package com.hnatiuk.screencapture.result

interface ScreenshotResult {

    fun observe(onSuccess: (Screenshot) -> Unit, onError: (Throwable) -> Unit): Subscription

    interface Subscription {
        fun dispose()
    }
}