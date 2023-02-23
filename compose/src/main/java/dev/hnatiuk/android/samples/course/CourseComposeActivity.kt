package dev.hnatiuk.android.samples.course

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.hnatiuk.android.samples.core.utils.SimpleIntentProvider

class CourseComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen("Olena Stepaniuk")
        }
    }

    companion object : SimpleIntentProvider(CourseComposeActivity::class.java)
}