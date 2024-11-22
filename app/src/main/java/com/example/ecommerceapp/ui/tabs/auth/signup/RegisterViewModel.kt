package com.example.ecommerceapp.ui.tabs.auth.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.model.SignupRequest
import com.example.domain.usecase.SaveSessionUseCase
import com.example.domain.usecase.SignupUseCase
import com.example.ecommerceapp.utils.DispatchersModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase,
    private val saveSessionUseCase: SaveSessionUseCase,
    @DispatchersModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel(), RegisterContract.ViewModel {

    private val _states = MutableStateFlow<RegisterContract.State>(
        RegisterContract.State.Nothing()
    )
    override val states = _states

    private val _events = MutableLiveData<RegisterContract.Event>()
    override val events = _events


    val userName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordConfirmation = MutableLiveData<String>()
    val phone = MutableLiveData<String>()

    val userNameError = MutableLiveData<String?>()
    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()
    val passwordConfirmationError = MutableLiveData<String?>()
    val phoneError = MutableLiveData<String?>()

    override fun invokeAction(action: RegisterContract.Action) {
        when (action) {
            is RegisterContract.Action.Register -> createUser()
        }
    }

    private fun createUser() {
        viewModelScope.launch(ioDispatcher) {

            if (!validForm()) return@launch
            val request = SignupRequest(
                name = userName.value,
                email = email.value,
                password = password.value,
                rePassword = passwordConfirmation.value,
                phone = phone.value
            )
            _states.emit(RegisterContract.State.Loading("Loading..."))
            signupUseCase.invoke(request)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _states.emit(
                                RegisterContract.State.Error(
                                    response.error?.message ?: "Unknown error"
                                )
                            )
                        }

                        is ResultWrapper.Loading -> {
                            _states.emit(RegisterContract.State.Loading("Loading..."))
                        }

                        is ResultWrapper.ServerError -> {
                            _states.emit(
                                RegisterContract.State.Error(
                                    response.serverError.serverMessage
                                )
                            )
                        }

                        is ResultWrapper.Success -> {
                            saveSessionUseCase.invoke(response.data)
                            _states.emit(RegisterContract.State.Success(response.data))
                            _events.postValue(RegisterContract.Event.NavigateToHome())
                        }
                    }
                }


        }
    }


    private fun validForm(): Boolean {
        var isValid = true
        if (userName.value.isNullOrBlank()) {
            userNameError.postValue("Please enter your user name")
            isValid = false
        } else {
            userNameError.postValue(null)
        }

        if (email.value.isNullOrBlank()) {
            emailError.postValue("Please enter your email")
            isValid = false
        } else {
            emailError.postValue(null)
        }
        if (phone.value.isNullOrBlank()) {
            phoneError.postValue("Please enter your phone number")
            isValid = false
        } else {
            phoneError.postValue(null)
        }
        if (password.value.isNullOrBlank()) {
            passwordError.postValue("Please enter your password")
            isValid = false
        } else {
            passwordError.postValue(null)
        }

        if (passwordConfirmation.value.isNullOrBlank()
            || passwordConfirmation.value != password.value
        ) {
            passwordConfirmationError.postValue("Password doesn't match")
            isValid = false
        } else {
            passwordConfirmationError.postValue(null)
        }
        return isValid
    }
}
