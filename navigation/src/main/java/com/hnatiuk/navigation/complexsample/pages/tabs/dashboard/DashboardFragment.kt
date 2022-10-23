package com.hnatiuk.navigation.complexsample.pages.tabs.dashboard

import androidx.fragment.app.viewModels
import com.hnatiuk.core.base.BaseVMFragment
import com.hnatiuk.core.base.Inflate
import com.hnatiuk.navigation.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseVMFragment<FragmentDashboardBinding, DashboardViewModel>() {

    override val bindingFactory: Inflate<FragmentDashboardBinding>
        get() = FragmentDashboardBinding::inflate

    override val viewModel by viewModels<DashboardViewModel>()
}