package com.hnatiuk.navigation.complexsample.auth.signin

import androidx.fragment.app.viewModels
import com.hnatiuk.core.base.BaseVMFragment
import com.hnatiuk.core.base.Inflate
import com.hnatiuk.navigation.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseVMFragment<FragmentSignInBinding, SignInViewModel>() {

    override val viewModel by viewModels<SignInViewModel>()

    override val bindingFactory: Inflate<FragmentSignInBinding>
        get() = FragmentSignInBinding::inflate

    override fun SignInViewModel.observeViewModel() {

    }
}