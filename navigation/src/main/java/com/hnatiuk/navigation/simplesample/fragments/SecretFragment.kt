package dev.hnatiuk.navigation.simplesample.fragments

import dev.hnatiuk.android.samples.core.base.BaseFragment
import dev.hnatiuk.android.samples.core.base.Inflate
import dev.hnatiuk.navigation.databinding.FragmentSecretBinding
import dev.hnatiuk.navigation.lib.asRoute
import dev.hnatiuk.navigation.lib.navController

class SecretFragment : BaseFragment<FragmentSecretBinding>() {

    override val bindingFactory: Inflate<FragmentSecretBinding>
        get() = FragmentSecretBinding::inflate

    override fun FragmentSecretBinding.initUI() {
        closeSecret.setOnClickListener {
            navController.popBackStack()
        }
        closeTheWholeBox.setOnClickListener {
            navController.popBackStack(dev.hnatiuk.navigation.simplesample.MainRoute.ROOT.asRoute, false)
        }
    }
}