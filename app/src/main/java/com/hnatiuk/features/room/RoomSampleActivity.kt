package com.hnatiuk.features.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hnatiuk.core.SimpleIntentProvider
import com.hnatiuk.features.R

class RoomSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_sample)
    }

    companion object : SimpleIntentProvider(RoomSampleActivity::class.java)
}

