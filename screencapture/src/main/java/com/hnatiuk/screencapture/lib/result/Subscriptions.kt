package dev.hnatiuk.screencapture.lib.result

object Subscriptions {

    private val DISPOSED_INSTANCE = object : ScreenshotResult.Subscription {

        override fun dispose() {
            //no op
        }
    }

    fun disposed(): ScreenshotResult.Subscription = DISPOSED_INSTANCE
}