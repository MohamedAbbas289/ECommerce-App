package com.example.ecommerceapp.ui.tabs.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.usecase.ClearCartUseCase
import com.example.domain.usecase.GetCartUseCase
import com.example.domain.usecase.GetSessionUserUseCase
import com.example.domain.usecase.RemoveFromCartUseCase
import com.example.ecommerceapp.utils.DispatchersModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val getSessionUserUseCase: GetSessionUserUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    @DispatchersModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel(), CartContract.ViewModel {
    private val _states = MutableStateFlow<CartContract.State>(
        CartContract.State.Loading()
    )
    override val states = _states

    private val _events = MutableLiveData<CartContract.Event>()
    override val events = _events

    override fun invokeAction(action: CartContract.Action) {
        when (action) {
            is CartContract.Action.LoadCart -> loadCartProducts()
            is CartContract.Action.RemoveProductFromCart -> removeProductFromCart(action.productId)
            is CartContract.Action.ClearCart -> clearUserCart()
        }
    }

    private fun clearUserCart() {
        viewModelScope.launch(ioDispatcher) {
            _states.emit(CartContract.State.LoadingRemovingProduct())
            val userResponse = getSessionUserUseCase.invoke()
            clearCartUseCase(userResponse?.token!!)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _states.emit(
                                CartContract.State.ErrorRemovingProduct(
                                    response.error?.message ?: "Something went wrong"
                                )
                            )
                        }

                        is ResultWrapper.Loading -> TODO()
                        is ResultWrapper.ServerError -> {
                            _states.emit(
                                CartContract.State.ErrorRemovingProduct(
                                    response.serverError.serverMessage
                                )
                            )
                        }

                        is ResultWrapper.Success -> {
                            _states.emit(
                                CartContract.State.ClearCartSuccess(
                                    "Cart cleared successfully"
                                )
                            )
                        }
                    }
                }
        }
    }

    private fun removeProductFromCart(productId: String) {
        viewModelScope.launch(ioDispatcher) {
            _states.emit(CartContract.State.LoadingRemovingProduct())
            val userResponse = getSessionUserUseCase.invoke()
            removeFromCartUseCase(productId, userResponse?.token!!)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _states.emit(
                                CartContract.State.ErrorRemovingProduct(
                                    response.error?.message ?: "Something went wrong"
                                )
                            )
                        }

                        is ResultWrapper.Loading -> TODO()
                        is ResultWrapper.ServerError -> {
                            _states.emit(
                                CartContract.State.ErrorRemovingProduct(
                                    response.serverError.serverMessage
                                )
                            )
                        }

                        is ResultWrapper.Success -> {
                            _states.emit(
                                CartContract.State.SuccessRemovingProduct(
                                    response.data.message ?: "Product removed successfully",
                                    response.data.cart!!
                                )
                            )
                        }
                    }
                }
        }
    }

    private fun loadCartProducts() {
        viewModelScope.launch(ioDispatcher) {
            val userResponse = getSessionUserUseCase.invoke()
            getCartUseCase(userResponse?.token!!)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _states.emit(
                                CartContract.State.Error(
                                    response.error?.message ?: "Something went wrong"
                                )
                            )
                        }

                        is ResultWrapper.Loading -> TODO()
                        is ResultWrapper.ServerError -> {
                            _states.emit(
                                CartContract.State.Error(
                                    response.serverError.serverMessage
                                )
                            )
                        }

                        is ResultWrapper.Success -> {
                            _states.emit(CartContract.State.Success(response.data.cart!!))
                        }
                    }
                }
        }
    }

}