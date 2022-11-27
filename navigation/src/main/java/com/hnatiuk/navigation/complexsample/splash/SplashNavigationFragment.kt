package dev.hnatiuk.navigation.complexsample.splash

import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import dev.hnatiuk.android.samples.core.base.BaseFragment
import dev.hnatiuk.android.samples.core.base.Inflate
import dev.hnatiuk.navigation.complexsample.pages.main.MainNavigationActivityArg
import dev.hnatiuk.navigation.complexsample.graphs.SplashDestination
import dev.hnatiuk.navigation.databinding.FragmentSplashNavigationBinding
import dev.hnatiuk.navigation.lib.buildRoute
import dev.hnatiuk.navigation.lib.navController

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