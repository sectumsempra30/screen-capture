package dev.hnatiuk.android.samples.navigation.lib

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import dev.hnatiuk.android.samples.core.base.BaseActivity
import dev.hnatiuk.android.samples.navigation.R

abstract class BaseHostActivity<VB : ViewBinding> : BaseActivity<VB>() {

    protected lateinit var navController: NavController

    abstract fun NavController.getGraph(): NavGraph

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        navController.graph = navController.getGraph()
    }
}