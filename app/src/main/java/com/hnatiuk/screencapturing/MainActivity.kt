package com.hnatiuk.screencapturing

import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.hnatiuk.screencapturing.R.id.makeScreenshot
import com.hnatiuk.screencapturing.R.id.screenshot
import com.hnatiuk.screencapturing.databinding.LayoutOverlayBinding
import com.hnatiuk.screencapturing.overlay.OverlayManager
import com.hnatiuk.screenshoter.GetMediaProjection
import com.hnatiuk.screenshoter.MediaProjectionResultApiDelegate
import com.hnatiuk.screenshoter.result.Screenshot
import com.hnatiuk.screenshoter.result.ScreenshotResult

class MainActivity : BaseActivity() {

//    private val getScreen = registerForActivityResult(GetMediaProjection(this)) {
//        screenshotManager.onActivityResult(SCREENSHOT_REQUEST_CODE, it.resultCode, it.data)
//    }
//
//    private lateinit var screenshotManager: ScreenshotManager
    private lateinit var screenshotManager: MediaProjectionResultApiDelegate
    private var screenshotResultSubscription: ScreenshotResult.Subscription? = null

    private val overlayManager by lazy { OverlayManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupScreenshotManager()
        initUI()
    }

    private fun setupScreenshotManager() {
        screenshotManager =  MediaProjectionResultApiDelegate(this, 888)
//        screenshotManager = ScreenshotManagerBuilder(this)
//            .withCustomActionOrder(ScreenshotActionOrder.mediaProjectionFirst())
//            .withPermissionRequestCode(SCREENSHOT_REQUEST_CODE)
//            .build()
    }

    private fun initUI() {
        R.id.startOverlay.asView<View>().setOnClickListener {
            startOverlay()
        }

        makeScreenshot.asView<Button>().setOnClickListener {
            makeScreenshot()
        }
    }

    private fun startOverlay() {
        val view = inflater.inflate(R.layout.layout_overlay, null)
        LayoutOverlayBinding.bind(view).apply {
            makeScreenshotOverlay.setOnClickListener {
                makeScreenshot()
            }
        }

        overlayManager.start(view)
    }

    private fun makeScreenshot() {
        screenshotResultSubscription = screenshotManager
            .makeScreenshot()
            .observe(
                onSuccess = { result ->
                    if (result is Screenshot.ScreenshotBitmap) {
                        screenshot.asView<ImageView>().setImageBitmap(result.bitmap)
                    }
                },
                onError = {
                    R.id.error.asView<TextView>().text = it.message
                }
            )
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        screenshotManager.onActivityResult(requestCode, resultCode, data)
//    }

    companion object {

        private const val SCREENSHOT_REQUEST_CODE = 888
    }
}