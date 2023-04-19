package dev.hnatiuk.android.samples.compose_bookshelf.presentation.pages.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.hnatiuk.android.samples.compose_bookshelf.presentation.pages.ui.theme.AndroidSamplesTheme
import dev.hnatiuk.android.samples.core.utils.SimpleIntentProvider

class BookShelfActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidSamplesTheme {
                BookShelfApp()
            }
        }
    }

    companion object : SimpleIntentProvider(BookShelfActivity::class.java)
}