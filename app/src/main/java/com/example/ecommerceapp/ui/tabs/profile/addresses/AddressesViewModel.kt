package com.example.ecommerceapp.ui.tabs.profile.addresses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.model.UserAddressRequest
import com.example.domain.usecase.AddAddressUseCase
import com.example.domain.usecase.GetAddressesUseCase
import com.example.domain.usecase.GetSessionUserUseCase
import com.example.domain.usecase.RemoveAddressUseCase
import com.example.ecommerceapp.utils.DispatchersModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressesViewModel @Inject constructor(
    private val addAddressUseCase: AddAddressUseCase,
    private val getAddressesUseCase: GetAddressesUseCase,
    private val removeAddressUseCase: RemoveAddressUseCase,
    private val getSessionUserUseCase: GetSessionUserUseCase,
    @DispatchersModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel(), AddressesContract.ViewModel {
    private val _state = MutableStateFlow<AddressesContract.State>(
        AddressesContract.State.Initial()
    )
    override val states = _state

    private val _event = MutableLiveData<AddressesContract.Event>()
    override val events = _event

    val addressName = MutableLiveData<String>()
    val details = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val city = MutableLiveData<String>()

    val addressNameError = MutableLiveData<String?>()
    val detailsError = MutableLiveData<String?>()
    val phoneError = MutableLiveData<String?>()
    val cityError = MutableLiveData<String?>()

    override fun invokeAction(action: AddressesContract.Action) {
        when (action) {
            is AddressesContract.Action.AddAddress -> addAddress()
            is AddressesContract.Action.LoadAddresses -> getAddresses()
            is AddressesContract.Action.RemoveAddress -> removeAddress(action.addressId)
        }
    }

    private fun removeAddress(addressId: String) {
        viewModelScope.launch(ioDispatcher) {
            _state.emit(AddressesContract.State.LoadingRemove())
            val userResponse = getSessionUserUseCase.invoke()
            removeAddressUseCase(addressId, userResponse?.token!!)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _state.emit(
                                AddressesContract.State.ErrorRemove(
                                    response.error?.message ?: "Unknown error"
                                )
                            )
                        }

                        is ResultWrapper.Loading -> TODO()
                        is ResultWrapper.ServerError -> {
                            _state.emit(
                                AddressesContract.State.ErrorRemove(
                                    response.serverError.serverMessage
                                )
                            )
                        }

                        is ResultWrapper.Success -> {
                            _state.emit(
                                AddressesContract.State.SuccessRemove(
                                    response.data.message ?: "Address removed successfully"
                                )
                            )
                        }
                    }
                }
        }
    }

    private fun getAddresses() {
        viewModelScope.launch {
            _state.emit(AddressesContract.State.Loading())
            val userResponse = getSessionUserUseCase.invoke()
            getAddressesUseCase(userResponse?.token!!)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _state.emit(
                                AddressesContract.State.Error(
                                    response.error?.message ?: "Something went wrong"
                                )
                            )
                        }

                        is ResultWrapper.Loading -> TODO()
                        is ResultWrapper.ServerError -> {
                            _state.emit(
                                AddressesContract.State.Error(
                                    response.serverError.serverMessage
                                )
                            )
                        }

                        is ResultWrapper.Success -> {
                            _state.emit(
                                AddressesContract.State.Success(
                                    response.data.addresses
                                )
                            )
                        }
                    }
                }
        }
    }

    private fun addAddress() {
        viewModelScope.launch(ioDispatcher) {
            if (!validForm()) return@launch
            _state.emit(AddressesContract.State.LoadingAdd())
            val userResponse = getSessionUserUseCase.invoke()
            val addAddressRequest = UserAddressRequest(
                name = addressName.value,
                details = details.value,
                phone = phone.value,
                city = city.value
            )
            addAddressUseCase(addAddressRequest, userResponse?.token!!)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _state.emit(
                                AddressesContract.State.ErrorAdd(
                                    response.error?.message ?: "Unknown error"
                                )
                            )
                        }

                        is ResultWrapper.Loading -> TODO()
                        is ResultWrapper.ServerError -> {
                            _state.emit(
                                AddressesContract.State.ErrorAdd(
                                    response.serverError.serverMessage
                                )
                            )
                        }

                        is ResultWrapper.Success -> {
                            _state.emit(
                                AddressesContract.State.SuccessAdd(
                                    response.data.message ?: "Address added successfully",
                                    response.data.addresses
                                )
                            )
                        }
                    }
                }
        }
    }

    private fun validForm(): Boolean {
        var isValid = true
        if (addressName.value.isNullOrBlank()) {
            addressNameError.postValue("Please enter your address name")
            isValid = false
        } else {
            addressNameError.postValue(null)
        }
        if (details.value.isNullOrBlank()) {
            detailsError.postValue("Please enter your details")
            isValid = false
        } else {
            detailsError.postValue(null)
        }
        if (phone.value.isNullOrBlank()) {
            phoneError.postValue("Please enter your phone number")
            isValid = false
        } else {
            phoneError.postValue(null)
        }
        if (city.value.isNullOrBlank()) {
            cityError.postValue("Please enter your city")
            isValid = false
        } else {
            cityError.postValue(null)
        }
        return isValid
    }

}