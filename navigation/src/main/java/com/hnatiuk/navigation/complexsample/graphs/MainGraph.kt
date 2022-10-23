package com.hnatiuk.navigation.complexsample.graphs

import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import com.hnatiuk.navigation.complexsample.pages.auth.signin.SignInFragment
import com.hnatiuk.navigation.complexsample.pages.auth.signup.SignUpFragment
import com.hnatiuk.navigation.complexsample.graphs.EntryDestination.*
import com.hnatiuk.navigation.complexsample.pages.tabs.host.TabsFragment

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