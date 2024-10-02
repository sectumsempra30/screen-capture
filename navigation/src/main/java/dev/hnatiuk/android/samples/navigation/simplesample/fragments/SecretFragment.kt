package dev.hnatiuk.android.samples.navigation.simplesample.fragments

import dev.hnatiuk.android.samples.core.base.BaseFragment
import dev.hnatiuk.android.samples.core.base.Inflate
import dev.hnatiuk.android.samples.navigation.databinding.FragmentSecretBinding
import dev.hnatiuk.android.samples.navigation.lib.asRoute
import dev.hnatiuk.android.samples.navigation.lib.navController

class SecretFragment : BaseFragment<FragmentSecretBinding>() {

    override val bindingFactory: Inflate<FragmentSecretBinding>
        get() = FragmentSecretBinding::inflate

    override fun FragmentSecretBinding.initUI() {
        closeSecret.setOnClickListener {
            navController.popBackStack()
        }
        closeTheWholeBox.setOnClickListener {
            //navController.popBackStack(dev.hnatiuk.android.samples.navigation.simplesample.MainRoute.ROOT.asRoute, false)
            navController.popBackStack()
        }
    }
}