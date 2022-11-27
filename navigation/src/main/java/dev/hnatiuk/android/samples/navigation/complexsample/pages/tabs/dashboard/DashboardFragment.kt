package dev.hnatiuk.android.samples.navigation.complexsample.pages.tabs.dashboard

import androidx.fragment.app.viewModels
import dev.hnatiuk.android.samples.core.base.BaseVMFragment
import dev.hnatiuk.android.samples.core.base.Inflate
import dev.hnatiuk.android.samples.navigation.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseVMFragment<FragmentDashboardBinding, DashboardViewModel>() {

    override val bindingFactory: Inflate<FragmentDashboardBinding>
        get() = FragmentDashboardBinding::inflate

    override val viewModel by viewModels<DashboardViewModel>()
}