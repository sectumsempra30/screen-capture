package com.hnatiuk.features.screencapture.lib

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import com.hnatiuk.core.mediaProjectionManager
import java.lang.ref.WeakReference

class ScreenCaptureRequest(
    context: Context,
) : ActivityResultContract<Unit, ActivityResult>() {

    private val contextReference = WeakReference(context)

    override fun createIntent(context: Context, input: Unit): Intent {
        return contextReference.get()
            ?.mediaProjectionManager
            ?.createScreenCaptureIntent()
            ?: throw ScreenshotException.NoContextReference
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ActivityResult {
        return ActivityResult(resultCode, intent)
    }
}