package dev.hnatiuk.android.samples.compose

import androidx.compose.runtime.Composable
import dev.hnatiuk.android.samples.core.utils.SimpleIntentProvider
import dev.hnatiuk.android.samples.compose.lib.BaseComposeActivity
import dev.hnatiuk.android.samples.compose.lib.ButtonSample

class ComposeActivity : BaseComposeActivity() {

    @Composable
    override fun Compose() = ButtonSample()

    companion object : SimpleIntentProvider(ComposeActivity::class.java)
}