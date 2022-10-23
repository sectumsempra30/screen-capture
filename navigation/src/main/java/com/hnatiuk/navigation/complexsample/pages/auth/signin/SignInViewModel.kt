package com.hnatiuk.navigation.complexsample.pages.auth.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hnatiuk.core.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class SignInEvent {

    object NavigateToSignUp : SignInEvent()

    object NavigateToAuthorizedFlow : SignInEvent()
}

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {

    private val email = MutableLiveData<String>()
    private val password = MutableLiveData<String>()

    private val event = SingleLiveEvent<SignInEvent>()

    fun signIn(email: String, password: String) {
        event.value = SignInEvent.NavigateToAuthorizedFlow
    }
}