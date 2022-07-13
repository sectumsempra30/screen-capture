package com.hnatiuk.features.navigation.fragments

import com.hnatiuk.entry.base.BaseFragment
import com.hnatiuk.features.databinding.FragmentSecretBinding
import com.hnatiuk.features.navigation.MainRoute
import com.hnatiuk.features.navigation.asRoute

class SecretFragment : BaseFragment<FragmentSecretBinding>(FragmentSecretBinding::inflate) {

    override fun FragmentSecretBinding.initUI() {
        closeSecret.setOnClickListener {
            navController.popBackStack()
        }
        closeTheWholeBox.setOnClickListener {
            navController.popBackStack(MainRoute.ROOT.asRoute, false)
        }
    }
}