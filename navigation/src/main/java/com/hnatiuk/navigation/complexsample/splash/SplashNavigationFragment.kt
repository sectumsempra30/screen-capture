package com.hnatiuk.navigation.complexsample.splash

import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import com.hnatiuk.core.base.BaseFragment
import com.hnatiuk.core.base.Inflate
import com.hnatiuk.navigation.complexsample.pages.main.MainNavigationActivityArg
import com.hnatiuk.navigation.complexsample.graphs.SplashDestination
import com.hnatiuk.navigation.databinding.FragmentSplashNavigationBinding
import com.hnatiuk.navigation.lib.buildRoute
import com.hnatiuk.navigation.lib.navController

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