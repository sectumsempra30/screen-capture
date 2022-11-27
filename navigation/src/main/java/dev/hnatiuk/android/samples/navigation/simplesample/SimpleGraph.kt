package dev.hnatiuk.android.samples.navigation.simplesample

import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import dev.hnatiuk.android.samples.navigation.lib.getDestinationNavType
import dev.hnatiuk.android.samples.navigation.simplesample.fragments.BoxArguments
import dev.hnatiuk.android.samples.navigation.simplesample.fragments.BoxFragment
import dev.hnatiuk.android.samples.navigation.simplesample.fragments.RootFragment
import dev.hnatiuk.android.samples.navigation.simplesample.fragments.SecretFragment

enum class MainRoute {
    ROOT,
    BOX,
    SECRET
}

fun NavController.createSimpleGraph() = createGraph(startDestination = MainRoute.ROOT.name) {
    fragment<RootFragment>(route = MainRoute.ROOT.name) {
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