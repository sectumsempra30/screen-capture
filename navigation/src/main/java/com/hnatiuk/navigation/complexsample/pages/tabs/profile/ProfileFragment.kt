package dev.hnatiuk.navigation.complexsample.pages.tabs.profile

import androidx.fragment.app.viewModels
import dev.hnatiuk.android.samples.core.base.BaseVMFragment
import dev.hnatiuk.android.samples.core.base.Inflate
import dev.hnatiuk.navigation.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseVMFragment<FragmentProfileBinding, ProfileViewModel>() {

    override val bindingFactory: Inflate<FragmentProfileBinding>
        get() = FragmentProfileBinding::inflate

    override val viewModel by viewModels<ProfileViewModel>()
}