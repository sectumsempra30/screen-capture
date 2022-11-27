package dev.hnatiuk.entry

import android.view.LayoutInflater
import dev.hnatiuk.core.base.BaseActivity
import dev.hnatiuk.core.utils.SimpleIntentProvider
import dev.hnatiuk.features.databinding.ActivityEntryBinding
import dev.hnatiuk.navigation.NavigationSampleActivity
import dev.hnatiuk.overlay.OverlayActivity
import dev.hnatiuk.screencapture.ScreenCaptureActivity
import dev.hnatiuk.compose.ComposeActivity
import dev.hnatiuk.motionlayout.MotionLayoutActivity
import dev.hnatiuk.room.RoomSampleActivity

class EntryActivity : BaseActivity<ActivityEntryBinding>() {

    override val bindingFactory: (LayoutInflater) -> ActivityEntryBinding =
        ActivityEntryBinding::inflate

    override fun ActivityEntryBinding.initUI() {
        toScreenCapture.setOnClickListener {
            startActivity(ScreenCaptureActivity.getIntent(this@EntryActivity))
        }
        toOverlay.setOnClickListener {
            startActivity(OverlayActivity.getIntent(this@EntryActivity))
        }
        toRoomSample.setOnClickListener {
            startActivity(RoomSampleActivity.getIntent(this@EntryActivity))
        }
        toNavigationSample.setOnClickListener {
            startActivity(NavigationSampleActivity.getIntent(this@EntryActivity))
        }
        toMotionLayoutSample.setOnClickListener {
            startActivity(MotionLayoutActivity.getIntent(this@EntryActivity))
        }
        toComposeSample.setOnClickListener {
            startActivity(ComposeActivity.getIntent(this@EntryActivity))
        }

        performSetup(activity = ComposeActivity)
    }

    private fun performSetup(activity: SimpleIntentProvider) {
        startActivity(activity.getIntent(this@EntryActivity))
    }
}