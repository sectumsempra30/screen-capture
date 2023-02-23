package dev.hnatiuk.android.samples

import android.view.LayoutInflater
import dev.hnatiuk.android.samples.compose.ComposeActivity
import dev.hnatiuk.android.samples.core.base.BaseActivity
import dev.hnatiuk.android.samples.core.utils.SimpleIntentProvider
import dev.hnatiuk.android.samples.course.CourseComposeActivity
import dev.hnatiuk.android.samples.databinding.ActivityEntryBinding
import dev.hnatiuk.android.samples.motionlayout.MotionLayoutActivity
import dev.hnatiuk.android.samples.navigation.NavigationSampleActivity
import dev.hnatiuk.android.samples.overlay.OverlayActivity
import dev.hnatiuk.android.samples.room.RoomSampleActivity
import dev.hnatiuk.android.samples.screencapture.ScreenCaptureActivity

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

        performSetup(activity = CourseComposeActivity)
    }

    private fun performSetup(activity: SimpleIntentProvider) {
        startActivity(activity.getIntent(this@EntryActivity))
    }
}