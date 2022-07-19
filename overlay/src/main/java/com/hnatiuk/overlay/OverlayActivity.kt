package com.hnatiuk.overlay

import android.os.Bundle
import android.view.LayoutInflater
import com.hnatiuk.core.base.BaseActivity
import com.hnatiuk.core.extensions.toast
import com.hnatiuk.core.utils.SimpleIntentProvider
import com.hnatiuk.overlay.databinding.ActivityOverlayBinding
import com.hnatiuk.overlay.databinding.LayoutOverlayBinding
import com.hnatiuk.overlay.lib.OverlayManager

class OverlayActivity : BaseActivity<ActivityOverlayBinding>() {

    override val bindingFactory: (LayoutInflater) -> ActivityOverlayBinding
        get() = ActivityOverlayBinding::inflate

    private val overlayManager by lazy { OverlayManager(this) }

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
        val view = LayoutOverlayBinding.inflate(layoutInflater).apply {
            click.setOnClickListener {
                toast("Toast from overlay")
            }
            stop.setOnClickListener {
                overlayManager.stop(this.root)
            }
        }.root
        overlayManager.start(view)
    }

    companion object : SimpleIntentProvider(OverlayActivity::class.java)
}