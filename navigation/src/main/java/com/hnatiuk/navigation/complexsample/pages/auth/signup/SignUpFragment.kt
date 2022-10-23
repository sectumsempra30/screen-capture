package com.hnatiuk.navigation.complexsample.pages.auth.signup

import androidx.fragment.app.viewModels
import com.hnatiuk.core.base.BaseVMFragment
import com.hnatiuk.core.base.Inflate
import com.hnatiuk.navigation.databinding.FragmentSignUpBinding

class SignUpFragment : BaseVMFragment<FragmentSignUpBinding, SignUpViewModel>() {

    override val bindingFactory: Inflate<FragmentSignUpBinding>
        get() = FragmentSignUpBinding::inflate

    override val viewModel by viewModels<SignUpViewModel>()
}