package dev.hnatiuk.navigation.complexsample.pages.auth.signup

import androidx.fragment.app.viewModels
import dev.hnatiuk.core.base.BaseVMFragment
import dev.hnatiuk.core.base.Inflate
import dev.hnatiuk.navigation.databinding.FragmentSignUpBinding

class SignUpFragment : BaseVMFragment<FragmentSignUpBinding, SignUpViewModel>() {

    override val bindingFactory: Inflate<FragmentSignUpBinding>
        get() = FragmentSignUpBinding::inflate

    override val viewModel by viewModels<SignUpViewModel>()
}