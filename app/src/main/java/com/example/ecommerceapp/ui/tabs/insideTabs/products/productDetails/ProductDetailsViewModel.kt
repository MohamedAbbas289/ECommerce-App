package com.example.ecommerceapp.ui.tabs.insideTabs.products.productDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.model.AddToWishListRequest
import com.example.domain.model.Product
import com.example.domain.usecase.AddProductToWishlistUseCase
import com.example.domain.usecase.GetSessionUserUseCase
import com.example.ecommerceapp.ui.tabs.insideTabs.products.ProductsContract
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
                                ProductDetailsContract.State.AddToWishlistSuccess(
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