package com.hnatiuk.screencapturing

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.hnatiuk.screencapturing.R.id.makeScreenshot
import com.hnatiuk.screencapturing.R.id.screenshot
import eu.bolt.screenshotty.*

class MainActivity : BaseActivity() {

    private lateinit var screenshotManager: ScreenshotManager
    private var screenshotResultSubscription: ScreenshotResult.Subscription? = null

    private val overlayManager by lazy { OverlayManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupScreenshotManager()
        initUI()
    }

    private fun setupScreenshotManager() {
        screenshotManager = ScreenshotManagerBuilder(this)
            .withCustomActionOrder(ScreenshotActionOrder.pixelCopyFirst())
            .withPermissionRequestCode(SCREENSHOT_REQUEST_CODE)
            .build()
    }

    private fun initUI() {
        R.id.startOverlay.asView<View>().setOnClickListener {
            startOverlay()
        }

        makeScreenshot.asView<Button>().setOnClickListener {
            screenshotResultSubscription = screenshotManager
                .makeScreenshot()
                .observe(
                    onSuccess = { result ->
                        if (result is ScreenshotBitmap) {
                            screenshot.asView<ImageView>().setImageBitmap(result.bitmap)
                        }
                    },
                    onError = {
                        R.id.error.asView<TextView>().text = it.message
                    }
                )
        }
    }

    private fun startOverlay() {
        overlayManager.start(R.layout.layout_overlay)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        screenshotManager.onActivityResult(requestCode, resultCode, data)
    }

    companion object {

        private const val SCREENSHOT_REQUEST_CODE = 888
    }
}