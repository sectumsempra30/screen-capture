package com.hnatiuk.navigation.complexsample.splash

import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import com.hnatiuk.core.base.BaseFragment
import com.hnatiuk.core.base.Inflate
import com.hnatiuk.navigation.complexsample.entry.EntryNavigationArg
import com.hnatiuk.navigation.complexsample.graphs.SplashRoute
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
        navController.navigate(buildRoute(SplashRoute.ENTRY, EntryNavigationArg(isSignIn = false)))
        requireActivity().finish()
    }
}