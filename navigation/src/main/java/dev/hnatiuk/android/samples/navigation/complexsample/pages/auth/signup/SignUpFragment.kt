package dev.hnatiuk.android.samples.navigation.complexsample.pages.auth.signup

import androidx.fragment.app.viewModels
import dev.hnatiuk.android.samples.core.base.BaseVMFragment
import dev.hnatiuk.android.samples.core.base.Inflate
import dev.hnatiuk.android.samples.navigation.databinding.FragmentSignUpBinding

class SignUpFragment : BaseVMFragment<FragmentSignUpBinding, SignUpViewModel>() {

    override val bindingFactory: Inflate<FragmentSignUpBinding>
        get() = FragmentSignUpBinding::inflate

    override val viewModel by viewModels<SignUpViewModel>()
}