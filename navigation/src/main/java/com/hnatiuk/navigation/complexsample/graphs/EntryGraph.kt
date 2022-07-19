package com.hnatiuk.navigation.complexsample.graphs

import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import com.hnatiuk.navigation.complexsample.auth.signin.SignInFragment
import com.hnatiuk.navigation.complexsample.auth.signup.SignUpFragment
import com.hnatiuk.navigation.complexsample.graphs.EntryRoute.*

enum class EntryRoute {
    SIGN_IN,
    SIGN_UP,
    HOME
}

fun NavController.getEntryGraph(startDestination: EntryRoute) = createGraph(startDestination = startDestination.name) {
    fragment<SignInFragment>(SIGN_IN.name)
    fragment<SignUpFragment>(SIGN_UP.name)
}