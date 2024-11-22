package com.example.ecommerceapp.ui.tabs.auth.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.model.LoginRequest
import com.example.domain.usecase.LoginUseCase
import com.example.domain.usecase.SaveSessionUseCase
import com.example.ecommerceapp.utils.DispatchersModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val saveSessionUseCase: SaveSessionUseCase,
    @DispatchersModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel(), LoginContract.ViewModel {
    private val _states = MutableStateFlow<LoginContract.State>(
        LoginContract.State.Nothing()
    )
    override val states = _states

    private val _events = MutableLiveData<LoginContract.Event>()
    override val events = _events

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    override fun invokeAction(action: LoginContract.Action) {
        when (action) {
            is LoginContract.Action.Login -> login()
        }
    }

    private fun login() {
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            if (!validForm()) return@launch
            val request = LoginRequest(
                email = email.value,
                password = password.value
            )
            _states.emit(LoginContract.State.Loading())
            loginUseCase.invoke(request)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _states.emit(
                                LoginContract.State.Error(
                                    response.error?.message ?: "An unknown error occurred"
                                )
                            )
                            Log.d("GTAG", "error: ${response.error?.message}")
                        }

                        is ResultWrapper.Loading -> {
                            _states.emit(LoginContract.State.Loading())
                            Log.d("GTAG", "loading")
                        }

                        is ResultWrapper.ServerError -> {
                            _states.emit(
                                LoginContract.State.Error(
                                    response.serverError.serverMessage
                                )
                            )
                            Log.d("GTAG", "server error: ${response.serverError.serverMessage}")
                        }

                        is ResultWrapper.Success -> {
                            saveSessionUseCase.invoke(response.data)
                            _states.emit(LoginContract.State.Success())
                            _events.postValue(LoginContract.Event.NavigateToHome())
                            Log.d("GTAG", "success")
                        }
                    }
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