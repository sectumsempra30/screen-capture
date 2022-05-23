package com.hnatiuk.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hnatiuk.presentation.databinding.ActivityMainBinding
import com.hnatiuk.presentation.databinding.LayoutOverlayBinding
import com.hnatiuk.presentation.overlay.OverlayManager
import com.hnatiuk.screencapture.ScreenCaptureAccessData
import com.hnatiuk.screencapture.ScreenCaptureRequest
import com.hnatiuk.screencapture.ScreenshotManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var screenshotManager: ScreenshotManager

    private lateinit var binding: ActivityMainBinding

    private val requestScreenshotPermission = registerForActivityResult(ScreenCaptureRequest(this)) {
        val data = it.data
        if (it.resultCode == RESULT_OK && data != null) {
            screenshotManager.init(this, ScreenCaptureAccessData(it.resultCode, data))
        }
    }

    private val overlayManager by lazy { OverlayManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() = with(binding) {
        startOverlay.setOnClickListener {
            startOverlay()
        }

        requestPermission.setOnClickListener {
            requestScreenshotPermission.launch(Unit)
        }

        makeScreenshot.setOnClickListener {
            makeScreenshot()
        }
    }

    private fun startOverlay() {
        val binding = LayoutOverlayBinding.inflate(layoutInflater).apply {
            makeScreenshotOverlay.setOnClickListener {
                makeScreenshot()
            }
        }
        overlayManager.start(binding.root)
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
}