package com.example.ecommerceapp.ui.tabs.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.LoginRequest
import com.example.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel(), LoginContract.ViewModel {
    private val _states = MutableLiveData<LoginContract.State>()
    override val states = _states

    private val _events = MutableLiveData<LoginContract.Event>()
    override val events = _events

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()

    override fun invokeAction(action: LoginContract.Action) {
        when (action) {
            is LoginContract.Action.Login -> login()
        }
    }

    private fun login() {
        viewModelScope.launch {
            try {
                if (!validForm()) return@launch
                val request = LoginRequest(
                    email = email.value,
                    password = password.value
                )
                _states.postValue(LoginContract.State.Loading())
                loginUseCase.invoke(request)
                _states.postValue(LoginContract.State.Success())
                _events.postValue(LoginContract.Event.NavigateToHome())
            } catch (e: Exception) {
                _states.postValue(
                    LoginContract.State.Error(e.localizedMessage ?: "Something went wrong")
                )
            }
        }
    }


    private fun validForm(): Boolean {
        var isValid = true

        if (email.value.isNullOrBlank()) {
            isValid = false
            emailError.postValue("please enter your email")
        } else {
            emailError.postValue(null)
        }
        if (password.value.isNullOrBlank()) {
            isValid = false
            passwordError.postValue("please enter your password")
        } else {
            passwordError.postValue(null)
        }
        return isValid
    }

}