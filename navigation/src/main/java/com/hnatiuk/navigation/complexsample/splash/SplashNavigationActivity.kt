package com.hnatiuk.navigation.complexsample.splash

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.navigation.NavController
import com.hnatiuk.core.utils.SimpleIntentProvider
import com.hnatiuk.navigation.complexsample.graphs.createSplashGraph
import com.hnatiuk.navigation.databinding.ActivityNavigationSplashBinding
import com.hnatiuk.navigation.lib.BaseHostActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashNavigationActivity : BaseHostActivity<ActivityNavigationSplashBinding>() {

    override val bindingFactory: (LayoutInflater) -> ActivityNavigationSplashBinding
        get() = ActivityNavigationSplashBinding::inflate

    override fun NavController.getGraph() = createSplashGraph()

    companion object : SimpleIntentProvider(SplashNavigationActivity::class.java)
}