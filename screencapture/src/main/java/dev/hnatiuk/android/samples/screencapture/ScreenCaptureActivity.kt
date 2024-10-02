package dev.hnatiuk.android.samples.screencapture

import android.view.LayoutInflater
import dev.hnatiuk.android.samples.core.base.BaseActivity
import dev.hnatiuk.android.samples.core.utils.SimpleIntentProvider
import dev.hnatiuk.android.samples.screencapture.databinding.ActivityScreenCaptureBinding
import dev.hnatiuk.android.samples.screencapture.lib.ScreenCaptureAccessData
import dev.hnatiuk.android.samples.screencapture.lib.ScreenCaptureRequest
import dev.hnatiuk.android.samples.screencapture.lib.ScreenshotManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class ScreenCaptureActivity : BaseActivity<ActivityScreenCaptureBinding>() {

    private val screenshotManager by lazy { ScreenshotManager(this) }

    override val bindingFactory: (LayoutInflater) -> ActivityScreenCaptureBinding
        get() = ActivityScreenCaptureBinding::inflate

//    private val requestScreenshotPermission = registerForActivityResult(ScreenCaptureRequest(this)) {
//        val data = it.data
//        if (it.resultCode == RESULT_OK && data != null) {
//            screenshotManager.onPermissionGranted(ScreenCaptureAccessData(it.resultCode, data))
//        }
//    }

    override fun ActivityScreenCaptureBinding.initUI() {
        requestPermission.setOnClickListener {
            //requestScreenshotPermission.launch(Unit)
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