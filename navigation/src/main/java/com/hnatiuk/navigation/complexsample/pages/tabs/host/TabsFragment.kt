package dev.hnatiuk.navigation.complexsample.pages.tabs.host

import androidx.fragment.app.viewModels
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController

import dev.hnatiuk.android.samples.core.base.Inflate
import dev.hnatiuk.navigation.R
import dev.hnatiuk.navigation.complexsample.graphs.TabsDestination
import dev.hnatiuk.navigation.complexsample.graphs.createTabsGraph
import dev.hnatiuk.navigation.databinding.FragmentTabsBinding
import dev.hnatiuk.navigation.lib.BaseHostFragment
import dev.hnatiuk.navigation.lib.asRoute
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