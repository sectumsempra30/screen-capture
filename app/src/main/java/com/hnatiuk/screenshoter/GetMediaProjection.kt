package com.hnatiuk.screenshoter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjectionManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import java.lang.ref.WeakReference

class GetMediaProjection(
    activity: Activity
) : ActivityResultContract<Unit, ActivityResult>() {

    private val mActivity = WeakReference(activity)

    override fun createIntent(context: Context, input: Unit?): Intent {
        val mediaProjectionManager = mActivity.get()
            ?.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as? MediaProjectionManager

        return mediaProjectionManager?.createScreenCaptureIntent()
            ?: throw IllegalArgumentException("Media projection manager cannot be null")
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ActivityResult {
        return ActivityResult(resultCode, intent)
    }
}