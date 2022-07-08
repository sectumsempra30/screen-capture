package com.hnatiuk.features.screencapture

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hnatiuk.features.R
import com.hnatiuk.features.databinding.ActivityScreenCaptureBinding
import com.hnatiuk.features.overlay.lib.OverlayManager
import com.hnatiuk.core.SimpleIntentProvider
import com.hnatiuk.features.screencapture.lib.ScreenCaptureAccessData
import com.hnatiuk.features.screencapture.lib.ScreenCaptureRequest
import com.hnatiuk.features.screencapture.lib.ScreenshotManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ScreenCaptureActivity : AppCompatActivity() {

    @Inject lateinit var screenshotManager: ScreenshotManager

    private val binding by viewBinding(ActivityScreenCaptureBinding::bind, R.id.container)

    private val requestScreenshotPermission = registerForActivityResult(ScreenCaptureRequest(this)) {
        val data = it.data
        if (it.resultCode == RESULT_OK && data != null) {
            screenshotManager.init(this, ScreenCaptureAccessData(it.resultCode, data))
        }
    }

    private val overlayManager by lazy { OverlayManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() = with(binding) {
        requestPermission.setOnClickListener {
            requestScreenshotPermission.launch(Unit)
        }

        makeScreenshot.setOnClickListener {
            makeScreenshot()
        }
    }

    private fun makeScreenshot() {
        screenshotManager.makeScreenshot()
            .subscribe(
                { result ->
                    binding.screenshot.setImageBitmap(result.bitmap)
                },
                { error ->
                    binding.error.text = error.message
                }
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        screenshotManager.onDestroy()
    }

    companion object : SimpleIntentProvider(ScreenCaptureActivity::class.java)
}