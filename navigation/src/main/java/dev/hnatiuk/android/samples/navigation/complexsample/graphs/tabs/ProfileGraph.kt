package dev.hnatiuk.android.samples.navigation.complexsample.graphs.tabs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import dev.hnatiuk.android.samples.navigation.complexsample.pages.tabs.profile.ProfileFragment

enum class ProfileDestinations {
    PROFILE
}

fun NavController.createProfileGraph() = createGraph(startDestination = ProfileDestinations.PROFILE.name) {
    fragment<ProfileFragment>(ProfileDestinations.PROFILE.name)
}