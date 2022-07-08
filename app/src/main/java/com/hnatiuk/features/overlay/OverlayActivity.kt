package com.hnatiuk.features.overlay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hnatiuk.core.toast
import com.hnatiuk.features.R
import com.hnatiuk.features.databinding.ActivityOverlayBinding
import com.hnatiuk.features.databinding.LayoutOverlayBinding
import com.hnatiuk.core.SimpleIntentProvider

class OverlayActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityOverlayBinding::bind, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overlay)
        initUI()
    }

    private fun initUI() = with(binding) {
        startOverlay.setOnClickListener {
            startOverlay()
        }
    }

    private fun startOverlay() {
        LayoutOverlayBinding.inflate(layoutInflater).apply {
            makeScreenshotOverlay.setOnClickListener {
                toast("Toast from overlay")
            }
        }
    }

    companion object : SimpleIntentProvider(OverlayActivity::class.java)
}