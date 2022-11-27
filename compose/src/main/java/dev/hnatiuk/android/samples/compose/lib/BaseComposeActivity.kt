package dev.hnatiuk.android.samples.compose.lib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import dev.hnatiuk.android.samples.core.utils.SimpleIntentProvider

abstract class BaseComposeActivity : ComponentActivity() {

    @Composable
    protected abstract fun Compose()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose()
        }
    }
}

