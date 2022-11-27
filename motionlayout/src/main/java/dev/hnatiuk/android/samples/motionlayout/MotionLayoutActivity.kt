package dev.hnatiuk.android.samples.motionlayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.hnatiuk.android.samples.core.utils.SimpleIntentProvider

class MotionLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_layout)
    }

    companion object : SimpleIntentProvider(MotionLayoutActivity::class.java)
}