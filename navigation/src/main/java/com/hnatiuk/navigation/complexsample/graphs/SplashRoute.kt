package com.hnatiuk.navigation.complexsample.graphs

import androidx.navigation.NavController
import androidx.navigation.activity
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import com.hnatiuk.navigation.complexsample.entry.EntryNavigationActivity
import com.hnatiuk.navigation.complexsample.entry.EntryNavigationArg
import com.hnatiuk.navigation.complexsample.splash.SplashNavigationFragment
import com.hnatiuk.navigation.lib.getDestinationNavType

enum class SplashRoute {
    SPLASH,
    ENTRY
}

fun NavController.createSplashGraph() = createGraph(startDestination = SplashRoute.SPLASH.name) {
    fragment<SplashNavigationFragment>(SplashRoute.SPLASH.name)
    activity("${SplashRoute.ENTRY.name}/{arg}") {
        activityClass = EntryNavigationActivity::class
        argument("arg") {
            type = getDestinationNavType<EntryNavigationArg>()
        }
    }
}