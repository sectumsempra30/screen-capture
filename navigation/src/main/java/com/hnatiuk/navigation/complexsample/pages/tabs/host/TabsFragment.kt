package com.hnatiuk.navigation.complexsample.pages.tabs.host

import androidx.fragment.app.viewModels
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController

import com.hnatiuk.core.base.Inflate
import com.hnatiuk.navigation.R
import com.hnatiuk.navigation.complexsample.graphs.TabsDestination
import com.hnatiuk.navigation.complexsample.graphs.createTabsGraph
import com.hnatiuk.navigation.databinding.FragmentTabsBinding
import com.hnatiuk.navigation.lib.BaseHostFragment
import com.hnatiuk.navigation.lib.asRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabsFragment : BaseHostFragment<FragmentTabsBinding, TabsViewModel>() {

    override val bindingFactory: Inflate<FragmentTabsBinding>
        get() = FragmentTabsBinding::inflate

    override val viewModel by viewModels<TabsViewModel>()

    override fun getContainerId() = R.id.tabsContainer

    override fun FragmentTabsBinding.initUI() {
        nestedNavController.graph = nestedNavController.createTabsGraph()
        bottomNavigation.setupWithNavController(nestedNavController)
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.profile -> nestedNavController.navigate(TabsDestination.PROFILE_1.asRoute)
                R.id.dashboard -> nestedNavController.navigate(TabsDestination.DASHBOARD_1.asRoute)
            }
            true
        }


        //NavigationUI.setupWithNavController(binding.bottomNavigation, nestedNavController)
    }
}