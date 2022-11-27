package dev.hnatiuk.navigation.complexsample.pages.auth.signin

import androidx.fragment.app.viewModels
import dev.hnatiuk.android.samples.core.base.BaseVMFragment
import dev.hnatiuk.android.samples.core.base.Inflate
import dev.hnatiuk.navigation.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseVMFragment<FragmentSignInBinding, SignInViewModel>() {

    override val viewModel by viewModels<SignInViewModel>()

    override val bindingFactory: Inflate<FragmentSignInBinding>
        get() = FragmentSignInBinding::inflate

    override fun SignInViewModel.observeViewModel() {

    }
}