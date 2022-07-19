package com.hnatiuk.navigation.complexsample.tabs.profile

import androidx.fragment.app.viewModels
import com.hnatiuk.core.base.BaseVMFragment
import com.hnatiuk.core.base.Inflate
import com.hnatiuk.navigation.databinding.FragmentProfileBinding

class ProfileFragment : BaseVMFragment<FragmentProfileBinding, ProfileViewModel>() {

    override val bindingFactory: Inflate<FragmentProfileBinding>
        get() = FragmentProfileBinding::inflate

    override val viewModel by viewModels<ProfileViewModel>()
}