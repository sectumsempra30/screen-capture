package com.hnatiuk.navigation.simplesample.fragments

import com.hnatiuk.core.base.BaseFragment
import com.hnatiuk.core.base.Inflate
import com.hnatiuk.navigation.databinding.FragmentSecretBinding
import com.hnatiuk.navigation.lib.asRoute
import com.hnatiuk.navigation.lib.navController

class SecretFragment : BaseFragment<FragmentSecretBinding>() {

    override val bindingFactory: Inflate<FragmentSecretBinding>
        get() = FragmentSecretBinding::inflate

    override fun FragmentSecretBinding.initUI() {
        closeSecret.setOnClickListener {
            navController.popBackStack()
        }
        closeTheWholeBox.setOnClickListener {
            navController.popBackStack(com.hnatiuk.navigation.simplesample.MainRoute.ROOT.asRoute, false)
        }
    }
}