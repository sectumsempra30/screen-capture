package com.hnatiuk.screencapture

import android.view.LayoutInflater
import com.hnatiuk.core.base.BaseActivity
import com.hnatiuk.core.utils.SimpleIntentProvider
import com.hnatiuk.screencapture.databinding.ActivityScreenCaptureBinding
import com.hnatiuk.screencapture.lib.ScreenCaptureAccessData
import com.hnatiuk.screencapture.lib.ScreenCaptureRequest
import com.hnatiuk.screencapture.lib.ScreenshotManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class ScreenCaptureActivity : BaseActivity<ActivityScreenCaptureBinding>() {

    private val screenshotManager by lazy { ScreenshotManager(this) }

    override val bindingFactory: (LayoutInflater) -> ActivityScreenCaptureBinding
        get() = ActivityScreenCaptureBinding::inflate

    private val requestScreenshotPermission = registerForActivityResult(ScreenCaptureRequest(this)) {
        val data = it.data
        if (it.resultCode == RESULT_OK && data != null) {
            screenshotManager.onPermissionGranted(ScreenCaptureAccessData(it.resultCode, data))
        }
    }

    override fun ActivityScreenCaptureBinding.initUI() {
        requestPermission.setOnClickListener {
            requestScreenshotPermission.launch(Unit)
        }

        makeScreenshot.setOnClickListener {
            makeScreenshot()
        }
    }

    private fun makeScreenshot() {
        screenshotManager.makeScreenshot("some-id")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> binding.screenshot.setImageBitmap(result.bitmap) },
                { error -> binding.error.text = error.message }
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        screenshotManager.onDestroy()
    }

    companion object : SimpleIntentProvider(ScreenCaptureActivity::class.java)
}