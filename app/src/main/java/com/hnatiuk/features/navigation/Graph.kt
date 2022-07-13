package com.hnatiuk.features.navigation

import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import com.hnatiuk.features.navigation.fragments.BoxFragment
import com.hnatiuk.features.navigation.fragments.BoxArguments
import com.hnatiuk.features.navigation.fragments.RootFragment
import com.hnatiuk.features.navigation.fragments.SecretFragment

enum class MainRoute {
    ROOT,
    BOX,
    SECRET
}

fun NavController.createMainGraph() = createGraph(startDestination = MainRoute.ROOT.name) {
    fragment<RootFragment>(MainRoute.ROOT.name) {
        label = "RootFragment"
    }
    fragment<BoxFragment>("${MainRoute.BOX}/{arg}") {
        label = "BoxFragment"
        argument("arg") {
            type = getDestinationNavType<BoxArguments>()
        }
    }
    fragment<SecretFragment>(MainRoute.SECRET.name) {
        label = "SecretFragment"
    }
}