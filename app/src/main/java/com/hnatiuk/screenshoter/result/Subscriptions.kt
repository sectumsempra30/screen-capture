package com.hnatiuk.screenshoter.result

object Subscriptions {

    private val DISPOSED_INSTANCE = object : ScreenshotResult.Subscription {
        override fun dispose() {
            //NOP
        }
    }

    fun disposed(): ScreenshotResult.Subscription = DISPOSED_INSTANCE
}