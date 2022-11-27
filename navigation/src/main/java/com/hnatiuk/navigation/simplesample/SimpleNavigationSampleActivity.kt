package dev.hnatiuk.navigation.simplesample

import android.view.LayoutInflater
import androidx.navigation.NavController
import dev.hnatiuk.core.utils.SimpleIntentProvider
import dev.hnatiuk.navigation.databinding.ActivitySimpleNavigationSampleBinding
import dev.hnatiuk.navigation.lib.BaseHostActivity

class SimpleNavigationSampleActivity : BaseHostActivity<ActivitySimpleNavigationSampleBinding>() {

    override val bindingFactory: (LayoutInflater) -> ActivitySimpleNavigationSampleBinding
        get() = ActivitySimpleNavigationSampleBinding::inflate

    override fun NavController.getGraph() = navController.createSimpleGraph()

    companion object : SimpleIntentProvider(SimpleNavigationSampleActivity::class.java)
}