package dev.hnatiuk.android.samples.navigation.complexsample.splash

import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import dagger.hilt.android.AndroidEntryPoint
import dev.hnatiuk.android.samples.core.base.BaseFragment
import dev.hnatiuk.android.samples.core.base.Inflate
import dev.hnatiuk.android.samples.navigation.complexsample.pages.main.MainNavigationActivityArg
import dev.hnatiuk.android.samples.navigation.complexsample.graphs.SplashDestination
import dev.hnatiuk.android.samples.navigation.databinding.FragmentSplashNavigationBinding
import dev.hnatiuk.android.samples.navigation.lib.buildRoute
import dev.hnatiuk.android.samples.navigation.lib.navController

@AndroidEntryPoint
class SplashNavigationFragment : BaseFragment<FragmentSplashNavigationBinding>() {

    override val bindingFactory: Inflate<FragmentSplashNavigationBinding>
        get() = FragmentSplashNavigationBinding::inflate

    override fun FragmentSplashNavigationBinding.initUI() {
        Handler(Looper.getMainLooper()).postDelayed(delayInMillis = 2_000) {
            navigationToEntryActivity()
        }
    }

    private fun navigationToEntryActivity() {
        val arg = MainNavigationActivityArg(isSignIn = true)
        navController.navigate(buildRoute(SplashDestination.MAIN, arg))
        requireActivity().finish()
    }
}