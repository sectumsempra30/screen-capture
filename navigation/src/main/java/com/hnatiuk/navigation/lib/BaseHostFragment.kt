package dev.hnatiuk.navigation.lib

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import dev.hnatiuk.android.samples.core.base.BaseVMFragment

abstract class BaseHostFragment<VB : ViewBinding, VM : ViewModel> : BaseVMFragment<VB, VM>() {

    protected lateinit var nestedNavController: NavController

    @IdRes
    abstract fun getContainerId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initNavigation()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initNavigation() {
        val navHost = childFragmentManager.findFragmentById(getContainerId()) as NavHostFragment
        nestedNavController = navHost.navController
    }
}