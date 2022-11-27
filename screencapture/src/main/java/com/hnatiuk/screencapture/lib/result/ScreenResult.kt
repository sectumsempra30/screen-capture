package dev.hnatiuk.screencapture.lib.result

import dev.hnatiuk.screencapture.lib.internal.Utils
import dev.hnatiuk.screencapture.lib.internal.ScreenshotSpecs

class ScreenResult(val spec: ScreenshotSpecs? = null) : ScreenshotResult {

    private val subscriptions = ArrayList<SubscriptionImpl>()

    private var deliveredScreenshot: Screenshot? = null
    private var deliveredError: Throwable? = null

    fun onSuccess(screenshot: Screenshot) {
        checkResultNotSet()
        Utils.checkOnMainThread()
        deliveredScreenshot = screenshot
        subscriptions.forEach {
            it.onSuccess?.invoke(screenshot)
        }
        subscriptions.clear()
    }

    fun onError(error: Throwable) {
        checkResultNotSet()
        Utils.checkOnMainThread()
        deliveredError = error
        subscriptions.forEach {
            it.onError?.invoke(error)
        }
        subscriptions.clear()
    }

    override fun observe(
        onSuccess: (Screenshot) -> Unit,
        onError: (Throwable) -> Unit
    ): ScreenshotResult.Subscription {
        Utils.checkOnMainThread()
        val screenshot = deliveredScreenshot
        val error = deliveredError
        return when {
            screenshot != null -> {
                onSuccess(screenshot)
                Subscriptions.disposed()
            }
            error != null -> {
                onError(error)
                Subscriptions.disposed()
            }
            else -> {
                val newSubscription = SubscriptionImpl(onSuccess, onError)
                subscriptions.add(newSubscription)
                newSubscription
            }
        }
    }

    private fun checkResultNotSet() {
        if (deliveredScreenshot != null || deliveredError != null) {
            error("attempted to set ScreenshotResult content multiple times")
        }
    }

    private class SubscriptionImpl(
        var onSuccess: ((Screenshot) -> Unit)?,
        var onError: ((Throwable) -> Unit)?
    ) : ScreenshotResult.Subscription {

        override fun dispose() {
            Utils.checkOnMainThread()
            onSuccess = null
            onError = null
        }
    }

    companion object {

        fun error(e: Throwable) = ScreenResult().apply {
            onError(e)
        }
    }
}