package com.hnatiuk.navigation.complexsample.graphs

import androidx.navigation.NavController
import androidx.navigation.activity
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import com.hnatiuk.navigation.complexsample.pages.main.MainNavigationActivity
import com.hnatiuk.navigation.complexsample.pages.main.MainNavigationActivityArg
import com.hnatiuk.navigation.complexsample.splash.SplashNavigationFragment
import com.hnatiuk.navigation.lib.getDestinationNavType

enum class SplashDestination {
    SPLASH,
    MAIN
}

fun NavController.createSplashGraph() = createGraph(startDestination = SplashDestination.SPLASH.name) {
    fragment<SplashNavigationFragment>(SplashDestination.SPLASH.name)
    activity("${SplashDestination.MAIN.name}/{arg}") {
        activityClass = MainNavigationActivity::class
        argument("arg") {
            type = getDestinationNavType<MainNavigationActivityArg>()
        }
    }
}