package dev.hnatiuk.android.samples.navigation.complexsample.splash

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.navigation.NavController
import dev.hnatiuk.android.samples.core.utils.SimpleIntentProvider
import dev.hnatiuk.android.samples.navigation.complexsample.graphs.createSplashGraph
import dev.hnatiuk.android.samples.navigation.databinding.ActivityNavigationSplashBinding
import dev.hnatiuk.android.samples.navigation.lib.BaseHostActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashNavigationActivity : BaseHostActivity<ActivityNavigationSplashBinding>() {

    override val bindingFactory: (LayoutInflater) -> ActivityNavigationSplashBinding
        get() = ActivityNavigationSplashBinding::inflate

    override fun NavController.getGraph() = createSplashGraph()

    companion object : SimpleIntentProvider(SplashNavigationActivity::class.java)
}