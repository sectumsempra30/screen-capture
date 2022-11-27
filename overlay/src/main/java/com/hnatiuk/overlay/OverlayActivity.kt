package dev.hnatiuk.overlay

import android.view.LayoutInflater
import dev.hnatiuk.core.base.BaseActivity
import dev.hnatiuk.core.extensions.toast
import dev.hnatiuk.core.utils.SimpleIntentProvider
import dev.hnatiuk.overlay.databinding.ActivityOverlayBinding
import dev.hnatiuk.overlay.databinding.LayoutOverlayBinding
import dev.hnatiuk.overlay.lib.OverlayManager

class OverlayActivity : BaseActivity<ActivityOverlayBinding>() {

    private val overlayManager by lazy { OverlayManager(this) }

    override val bindingFactory: (LayoutInflater) -> ActivityOverlayBinding
        get() = ActivityOverlayBinding::inflate

    override fun ActivityOverlayBinding.initUI() {
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