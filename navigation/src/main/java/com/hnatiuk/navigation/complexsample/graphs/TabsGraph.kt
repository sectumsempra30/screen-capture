package dev.hnatiuk.navigation.complexsample.graphs

import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import androidx.navigation.navigation
import dev.hnatiuk.navigation.complexsample.graphs.TabsDestination.*
import dev.hnatiuk.navigation.complexsample.graphs.tabs.DashboardDestinations
import dev.hnatiuk.navigation.complexsample.graphs.tabs.ProfileDestinations
import dev.hnatiuk.navigation.complexsample.pages.tabs.dashboard.DashboardFragment
import dev.hnatiuk.navigation.complexsample.pages.tabs.profile.ProfileFragment

enum class TabsDestination {
    PROFILE_1,
    DASHBOARD_1,
    SETTINGS
}

fun NavController.createTabsGraph() = createGraph(startDestination = DASHBOARD_1.name) {
    navigation(DashboardDestinations.BOXES.name, DASHBOARD_1.name) {
        fragment<DashboardFragment>(DashboardDestinations.BOXES.name)
    }
    navigation(ProfileDestinations.PROFILE.name, PROFILE_1.name) {
        //setGraph(createProfileGraph(), null)
        fragment<ProfileFragment>(ProfileDestinations.PROFILE.name)
    }
}