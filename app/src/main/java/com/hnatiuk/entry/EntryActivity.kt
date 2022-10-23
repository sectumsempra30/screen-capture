package com.hnatiuk.entry

import android.view.LayoutInflater
import com.hnatiuk.core.base.BaseActivity
import com.hnatiuk.core.utils.SimpleIntentProvider
import com.hnatiuk.features.databinding.ActivityEntryBinding
import com.hnatiuk.navigation.NavigationSampleActivity
import com.hnatiuk.overlay.OverlayActivity
import com.hnatiuk.screencapture.ScreenCaptureActivity
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