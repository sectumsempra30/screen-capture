package com.hnatiuk.entry

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hnatiuk.features.R
import com.hnatiuk.features.databinding.ActivityEntryBinding
import com.hnatiuk.features.overlay.OverlayActivity
import com.hnatiuk.features.room.RoomSampleActivity
import com.hnatiuk.features.screencapture.ScreenCaptureActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityEntryBinding::bind, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
        initUI()
    }

    private fun initUI() = with(binding) {
        toScreenCapture.setOnClickListener {
            startActivity(ScreenCaptureActivity.getIntent(this@EntryActivity))
        }
        toOverlay.setOnClickListener {
            startActivity(OverlayActivity.getIntent(this@EntryActivity))
        }
        toRoomSample.setOnClickListener {
            startActivity(RoomSampleActivity.getIntent(this@EntryActivity))
        }
    }
}