package com.hnatiuk.screenshoter.result

interface ScreenshotResult {

    fun observe(onSuccess: (Screenshot) -> Unit, onError: (Throwable) -> Unit): Subscription

    interface Subscription {
        fun dispose()
    }
}