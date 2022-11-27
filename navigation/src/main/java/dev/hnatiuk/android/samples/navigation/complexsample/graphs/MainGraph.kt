package dev.hnatiuk.android.samples.navigation.complexsample.graphs

import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import dev.hnatiuk.android.samples.navigation.complexsample.pages.auth.signin.SignInFragment
import dev.hnatiuk.android.samples.navigation.complexsample.pages.auth.signup.SignUpFragment
import dev.hnatiuk.android.samples.navigation.complexsample.graphs.EntryDestination.*
import dev.hnatiuk.android.samples.navigation.complexsample.pages.tabs.host.TabsFragment

enum class EntryDestination {
    SIGN_IN,
    SIGN_UP,
    TABS,
    EDIT_PROFILE
}

fun NavController.getEntryGraph(startDestination: EntryDestination) = createGraph(startDestination = startDestination.name) {
    fragment<SignInFragment>(SIGN_IN.name)
    fragment<SignUpFragment>(SIGN_UP.name)
    fragment<TabsFragment>(TABS.name)
}