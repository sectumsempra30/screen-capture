package dev.hnatiuk.android.samples.navigation.simplesample

import android.view.LayoutInflater
import androidx.navigation.NavController
import dev.hnatiuk.android.samples.core.utils.SimpleIntentProvider
import dev.hnatiuk.android.samples.navigation.databinding.ActivitySimpleNavigationSampleBinding
import dev.hnatiuk.android.samples.navigation.lib.BaseHostActivity

class SimpleNavigationSampleActivity : BaseHostActivity<ActivitySimpleNavigationSampleBinding>() {

    override val bindingFactory: (LayoutInflater) -> ActivitySimpleNavigationSampleBinding
        get() = ActivitySimpleNavigationSampleBinding::inflate

    override fun NavController.getGraph() = navController.createSimpleGraph()

    companion object : SimpleIntentProvider(SimpleNavigationSampleActivity::class.java)
}