package dev.hnatiuk.compose

import androidx.compose.runtime.Composable
import dev.hnatiuk.core.utils.SimpleIntentProvider
import dev.hnatiuk.compose.lib.BaseComposeActivity
import dev.hnatiuk.compose.lib.ButtonSample

class ComposeActivity : BaseComposeActivity() {

    @Composable
    override fun Compose() = ButtonSample()

    companion object : SimpleIntentProvider(ComposeActivity::class.java)
}