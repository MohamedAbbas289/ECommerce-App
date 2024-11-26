package com.example.ecommerceapp.ui.tabs.profile.userInfo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.model.UpdateProfileRequest
import com.example.domain.usecase.GetSessionUserUseCase
import com.example.domain.usecase.SaveSessionUseCase
import com.example.domain.usecase.UpdateProfileUseCase
import com.example.ecommerceapp.utils.DispatchersModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val getSessionUserUseCase: GetSessionUserUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val saveSessionUseCase: SaveSessionUseCase,
    @DispatchersModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel(), UserInfoContract.ViewModel {
    private val _states = MutableStateFlow<UserInfoContract.State>(
        UserInfoContract.State.Initial()
    )
    override val states = _states

    private val _events = MutableLiveData<UserInfoContract.Event>()
    override val events = _events

    val userName = MutableLiveData<String?>()
    val email = MutableLiveData<String?>()
    val phone = MutableLiveData<String?>()

    val userNameError = MutableLiveData<String?>()
    val emailError = MutableLiveData<String?>()
    val phoneError = MutableLiveData<String?>()

    override fun invokeAction(action: UserInfoContract.Action) {
        when (action) {
            is UserInfoContract.Action.UpdateUser -> updateUserData(
                action.userName,
                action.email,
                action.phone
            )

            is UserInfoContract.Action.GetUser -> getUserData()
        }
    }

    private fun getUserData() {
        viewModelScope.launch(ioDispatcher) {
            try {
                val userResponse = getSessionUserUseCase.invoke()
                Log.d("GTAG", "getUserData: $userResponse")
                userName.postValue(userResponse?.user?.name)
                email.postValue(userResponse?.user?.email)
                _states.emit(UserInfoContract.State.Success())
            } catch (e: Exception) {
                _states.emit(
                    UserInfoContract.State.Error(
                        e.localizedMessage ?: "Something went wrong"
                    )
                )
            }
        }
    }


    private fun updateUserData(userName: String, email: String, phone: String) {
        viewModelScope.launch(ioDispatcher) {
            val updateProfileRequest = UpdateProfileRequest(
                name = userName.ifBlank { null },
                email = email.ifBlank { null },
                phone = phone.ifBlank { null }
            )
            if (userName.isBlank() && email.isBlank() && phone.isBlank()) {
                _states.emit(UserInfoContract.State.UpdateUserError("Please fill at least one field"))
                return@launch
            }
            val userResponse = getSessionUserUseCase.invoke()
            _states.emit(UserInfoContract.State.Loading())
            updateProfileUseCase.invoke(updateProfileRequest, userResponse?.token!!)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _states.emit(
                                UserInfoContract.State.UpdateUserError(
                                    response.error?.message ?: "Couldn't update user"
                                )
                            )
                        }

                        is ResultWrapper.Loading -> {
                            //_states.emit(UserInfoContract.State.Loading())
                        }

                        is ResultWrapper.ServerError -> {
                            _states.emit(
                                UserInfoContract.State.UpdateUserError(
                                    response.serverError.serverMessage
                                )
                            )
                        }

                        is ResultWrapper.Success -> {
                            _states.emit(
                                UserInfoContract.State.UpdateUserSuccess(
                                    response.data.message ?: "Success"
                                )
                            )
                            response.data.token = userResponse.token
                            saveSessionUseCase.invoke(userResponse = response.data)
                            Log.d("GTAG", "updatedUserData: ${response.data}")
                            _events.postValue(UserInfoContract.Event.NavigateToProfile())
                        }
                    }
                }
        }
    }

}