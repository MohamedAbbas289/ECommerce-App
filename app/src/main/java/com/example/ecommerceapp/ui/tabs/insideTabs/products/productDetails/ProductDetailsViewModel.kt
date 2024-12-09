package com.example.ecommerceapp.ui.tabs.insideTabs.products.productDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.model.AddToCartRequest
import com.example.domain.model.AddToWishListRequest
import com.example.domain.model.Product
import com.example.domain.model.UpdateQuantityRequest
import com.example.domain.usecase.AddProductToWishlistUseCase
import com.example.domain.usecase.AddToCartUseCase
import com.example.domain.usecase.GetSessionUserUseCase
import com.example.domain.usecase.UpdateProductQuantityUseCase
import com.example.ecommerceapp.utils.DispatchersModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val addProductToWishlistUseCase: AddProductToWishlistUseCase,
    private val getSessionUserUseCase: GetSessionUserUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val updateProductQuantityUseCase: UpdateProductQuantityUseCase,
    @DispatchersModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel(), ProductDetailsContract.ViewModel {
    private val _state = MutableStateFlow<ProductDetailsContract.State>(
        ProductDetailsContract.State.Initial()
    )
    override val states = _state

    private val _event = MutableLiveData<ProductDetailsContract.Event>()
    override val events = _event


    override fun invokeAction(action: ProductDetailsContract.Action) {
        when (action) {
            is ProductDetailsContract.Action.AddToWishlistClicked -> addProductToWishlist(action.product)
            is ProductDetailsContract.Action.AddToCartClicked -> addProductToCart(action.product)
            is ProductDetailsContract.Action.UpdateQuantity -> updateProductQuantity(
                action.product,
                action.quantity
            )
        }
    }

    private fun updateProductQuantity(product: Product, quantity: Int) {
        viewModelScope.launch(ioDispatcher) {
            _state.emit(ProductDetailsContract.State.Loading())
            val userResponse = getSessionUserUseCase()
            val updateQuantityRequest = UpdateQuantityRequest(quantity)
            updateProductQuantityUseCase.invoke(
                productId = product.id!!,
                updateQuantityRequest = updateQuantityRequest,
                token = userResponse?.token!!
            )
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _state.emit(
                                ProductDetailsContract.State.Error(
                                    response.error?.message ?: "Something went wrong"
                                )
                            )
                        }

                        is ResultWrapper.Loading -> TODO()
                        is ResultWrapper.ServerError -> {
                            _state.emit(
                                ProductDetailsContract.State.Error(
                                    response.serverError.serverMessage
                                )
                            )
                        }

                        is ResultWrapper.Success -> {
                            _state.emit(
                                ProductDetailsContract.State.SuccessAddToCart(
                                    product,
                                    response.data.message ?: "Product quantity updated successfully"
                                )
                            )
                        }
                    }
                }

        }
    }


    private fun addProductToCart(product: Product) {
        viewModelScope.launch(ioDispatcher) {
            _state.emit(ProductDetailsContract.State.Loading())
            val userResponse = getSessionUserUseCase()
            val addToCartRequest = AddToCartRequest(product.id)
            addToCartUseCase(addToCartRequest, userResponse?.token!!)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _state.emit(
                                ProductDetailsContract.State.Error(
                                    response.error?.message ?: "Something went wrong"
                                )
                            )
                        }

                        is ResultWrapper.Loading -> TODO()
                        is ResultWrapper.ServerError -> {
                            _state.emit(
                                ProductDetailsContract.State.Error(
                                    response.serverError.serverMessage
                                )
                            )
                        }

                        is ResultWrapper.Success -> {
                            _state.emit(
                                ProductDetailsContract.State.SuccessAddToCart(
                                    product,
                                    response.data.message
                                        ?: "Product added successfully to your cart"
                                )
                            )
                        }
                    }
                }
        }
    }

    private fun addProductToWishlist(product: Product) {
        viewModelScope.launch(ioDispatcher) {
            _state.emit(ProductDetailsContract.State.Loading())
            val userResponse = getSessionUserUseCase.invoke()
            val addToWishListRequest = AddToWishListRequest(product.id)
            addProductToWishlistUseCase.invoke(addToWishListRequest, userResponse?.token!!)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _state.emit(
                                ProductDetailsContract.State.Error(
                                    response.error?.message ?: "Something went wrong"
                                )
                            )
                        }

                        is ResultWrapper.Loading -> TODO()
                        is ResultWrapper.ServerError -> {
                            _state.emit(
                                ProductDetailsContract.State.Error(
                                    response.serverError.serverMessage
                                )
                            )
                        }

                        is ResultWrapper.Success -> {
                            _state.emit(
                                ProductDetailsContract.State.SuccessAddToWishlist(
                                    product,
                                    response.data.message ?: "Product added successfully"
                                )
                            )
                        }
                    }
                }
        }
    }

}