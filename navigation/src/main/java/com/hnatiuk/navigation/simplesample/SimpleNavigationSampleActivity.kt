package com.hnatiuk.navigation.simplesample

import android.view.LayoutInflater
import androidx.navigation.NavController
import com.hnatiuk.core.utils.SimpleIntentProvider
import com.hnatiuk.navigation.databinding.ActivitySimpleNavigationSampleBinding
import com.hnatiuk.navigation.lib.BaseHostActivity

class SimpleNavigationSampleActivity : BaseHostActivity<ActivitySimpleNavigationSampleBinding>() {

    override val bindingFactory: (LayoutInflater) -> ActivitySimpleNavigationSampleBinding
        get() = ActivitySimpleNavigationSampleBinding::inflate

    override fun NavController.getGraph() = navController.createSimpleGraph()

    companion object : SimpleIntentProvider(SimpleNavigationSampleActivity::class.java)
}