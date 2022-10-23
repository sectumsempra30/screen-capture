package com.hnatiuk.navigation.complexsample.pages.tabs.profile

import androidx.fragment.app.viewModels
import com.hnatiuk.core.base.BaseVMFragment
import com.hnatiuk.core.base.Inflate
import com.hnatiuk.navigation.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseVMFragment<FragmentProfileBinding, ProfileViewModel>() {

    override val bindingFactory: Inflate<FragmentProfileBinding>
        get() = FragmentProfileBinding::inflate

    override val viewModel by viewModels<ProfileViewModel>()
}