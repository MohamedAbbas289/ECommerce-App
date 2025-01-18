package com.example.ecommerceapp.ui.tabs.checkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.model.OrderRequest
import com.example.domain.model.UserAddressRequest
import com.example.domain.usecase.AddAddressUseCase
import com.example.domain.usecase.CreateCashOrderUseCase
import com.example.domain.usecase.CreateOnlineOrderUseCase
import com.example.domain.usecase.GetAddressesUseCase
import com.example.domain.usecase.GetSessionUserUseCase
import com.example.ecommerceapp.utils.DispatchersModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val createCashOrderUseCase: CreateCashOrderUseCase,
    private val createOnlineOrderUseCase: CreateOnlineOrderUseCase,
    private val getAddressesUseCase: GetAddressesUseCase,
    private val getSessionUserUseCase: GetSessionUserUseCase,
    @DispatchersModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel(), CheckoutContract.ViewModel {
    private val _state = MutableStateFlow<CheckoutContract.State>(
        CheckoutContract.State.Initial
    )
    override val state = _state

    private val _event = MutableLiveData<CheckoutContract.Event>()
    override val event = _event


    override fun invokeAction(action: CheckoutContract.Action) {
        when (action) {
            is CheckoutContract.Action.CreateCashOrder -> createCashOrder(
                action.cartId,
                action.orderRequest
            )

            is CheckoutContract.Action.LoadUserAddresses -> loadUserAddresses()
            is CheckoutContract.Action.CreateCreditCardOrder -> createCreditCardOrder(
                action.cartId,
                action.orderRequest
            )
        }
    }

    private fun createCreditCardOrder(cartId: String, orderRequest: OrderRequest) {
        viewModelScope.launch(ioDispatcher) {
            _state.emit(CheckoutContract.State.LoadingOrder())
            val userResponse = getSessionUserUseCase.invoke()
            createOnlineOrderUseCase(orderRequest, cartId, userResponse?.token!!)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _state.emit(
                                CheckoutContract.State.ErrorOrder(
                                    response.error?.message ?: "Something went wrong"
                                )
                            )
                        }

                        is ResultWrapper.Loading -> TODO()
                        is ResultWrapper.ServerError -> {
                            _state.emit(
                                CheckoutContract.State.ErrorOrder(
                                    response.serverError.serverMessage
                                )
                            )
                        }

                        is ResultWrapper.Success -> {
                            _state.emit(
                                CheckoutContract.State.SuccessOnlineOrder(
                                    response.data.session!!
                                )
                            )
                        }
                    }
                }
        }
    }


    private fun loadUserAddresses() {
        viewModelScope.launch(ioDispatcher) {
            _state.emit(CheckoutContract.State.Loading())
            val userResponse = getSessionUserUseCase.invoke()
            getAddressesUseCase(userResponse?.token!!)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _state.emit(
                                CheckoutContract.State.Error(
                                    response.error?.message ?: "Something went wrong"
                                )
                            )
                        }

                        is ResultWrapper.Loading -> TODO()
                        is ResultWrapper.ServerError -> {
                            _state.emit(
                                CheckoutContract.State.Error(
                                    response.serverError.serverMessage
                                )
                            )
                        }

                        is ResultWrapper.Success -> {
                            _state.emit(
                                CheckoutContract.State.Success(
                                    response.data.addresses!!
                                )
                            )
                        }
                    }
                }
        }
    }

    private fun createCashOrder(cartId: String, orderRequest: OrderRequest) {
        viewModelScope.launch(ioDispatcher) {
            _state.emit(CheckoutContract.State.LoadingOrder())
            val userResponse = getSessionUserUseCase.invoke()
            createCashOrderUseCase(
                orderRequest = orderRequest,
                token = userResponse?.token!!,
                cartId = cartId
            ).collect { response ->
                when (response) {
                    is ResultWrapper.Error -> {
                        _state.emit(
                            CheckoutContract.State.ErrorOrder(
                                response.error?.message ?: "Something went wrong"
                            )
                        )
                    }

                    is ResultWrapper.Loading -> TODO()
                    is ResultWrapper.ServerError -> {
                        _state.emit(
                            CheckoutContract.State.ErrorOrder(
                                response.serverError.serverMessage
                            )
                        )
                    }

                    is ResultWrapper.Success -> {
                        _state.emit(CheckoutContract.State.SuccessOrder())
                        _event.postValue(
                            CheckoutContract.Event.NavigateToOrderCompleteScreen(
                                response.data.orderDetails!!
                            )
                        )
                    }
                }
            }
        }
    }


}