package com.example.ecommerceapp.ui.tabs.wishlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.model.AddToCartRequest
import com.example.domain.usecase.AddToCartUseCase
import com.example.domain.usecase.GetSessionUserUseCase
import com.example.domain.usecase.GetWishlistUseCase
import com.example.domain.usecase.RemoveProductFromWishlistUseCase
import com.example.ecommerceapp.utils.DispatchersModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val getWishlistUseCase: GetWishlistUseCase,
    private val getSessionUserUseCase: GetSessionUserUseCase,
    private val removeProductFromWishlistUseCase: RemoveProductFromWishlistUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    @DispatchersModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel(), WishlistContract.ViewModel {
    private val _states = MutableStateFlow<WishlistContract.State>(
        WishlistContract.State.Loading()
    )
    override val states = _states

    private val _events = MutableLiveData<WishlistContract.Event>()
    override val events = _events

    override fun invokeAction(action: WishlistContract.Action) {
        when (action) {
            is WishlistContract.Action.LoadWishlist -> loadWishlist()
            is WishlistContract.Action.RemoveFromWishlist -> removeFromWishlist(action.productId)
            is WishlistContract.Action.AddToCart -> addProductToCart(action.productId)
        }
    }

    private fun addProductToCart(productId: String) {
        viewModelScope.launch(ioDispatcher) {
            _states.value = WishlistContract.State.RemoveFromWishlistLoading()
            val userResponse = getSessionUserUseCase.invoke()
            val addToCartRequest = AddToCartRequest(productId)
            addToCartUseCase.invoke(addToCartRequest, userResponse?.token!!)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _states.emit(
                                WishlistContract.State.RemoveFromWishlistError(
                                    response.error?.message ?: "Something went wrong"
                                )
                            )
                        }

                        is ResultWrapper.Loading -> TODO()
                        is ResultWrapper.ServerError -> {
                            _states.emit(
                                WishlistContract.State.RemoveFromWishlistError(
                                    response.serverError.serverMessage
                                )
                            )
                        }

                        is ResultWrapper.Success -> {
                            _states.emit(WishlistContract.State.AddToCartSuccess("Product added to cart"))
                        }
                    }
                }
        }
    }

    private fun removeFromWishlist(productId: String) {
        viewModelScope.launch(ioDispatcher) {
            _states.value = WishlistContract.State.RemoveFromWishlistLoading()
            val userResponse = getSessionUserUseCase.invoke()
            removeProductFromWishlistUseCase.invoke(productId, userResponse?.token!!)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _states.emit(
                                WishlistContract.State.RemoveFromWishlistError(
                                    response.error?.message ?: "Something went wrong"
                                )
                            )
                        }

                        is ResultWrapper.Loading -> TODO()
                        is ResultWrapper.ServerError -> {
                            _states.emit(
                                WishlistContract.State.RemoveFromWishlistError(
                                    response.serverError.serverMessage
                                )
                            )
                        }

                        is ResultWrapper.Success -> {
                            _states.emit(WishlistContract.State.RemoveFromWishlistSuccess())
                        }
                    }
                }
        }
    }

    private fun loadWishlist() {
        viewModelScope.launch(ioDispatcher) {
            _states.value = WishlistContract.State.Loading()
            val userResponse = getSessionUserUseCase.invoke()
            getWishlistUseCase.invoke(userResponse?.token!!)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _states.emit(
                                WishlistContract.State.Error(
                                    response.error?.message ?: "Something went wrong"
                                )
                            )
                        }

                        is ResultWrapper.Loading -> TODO()
                        is ResultWrapper.ServerError -> {
                            _states.emit(
                                WishlistContract.State.Error(
                                    response.serverError.serverMessage
                                )
                            )
                        }

                        is ResultWrapper.Success -> {
                            _states.emit(
                                WishlistContract.State.Success(
                                    response.data
                                )
                            )
                        }
                    }
                }
        }
    }

}