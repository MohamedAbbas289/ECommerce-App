package com.example.ecommerceapp.ui.tabs.insideTabs.products

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Brand
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.SubCategory
import com.example.domain.usecase.GetProductsUseCase
import com.example.ecommerceapp.utils.DispatchersModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productsUseCase: GetProductsUseCase,
    @DispatchersModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel(), ProductsContract.ViewModel {
    private val _state = MutableStateFlow<ProductsContract.State>(
        ProductsContract.State.Loading(message = "Loading")
    )
    override val states = _state

    private val _event = MutableLiveData<ProductsContract.Event>()
    override val events = _event
    override fun invokeAction(action: ProductsContract.Action) {
        when (action) {
            is ProductsContract.Action.LoadProductsByCategory -> loadProductsByCategory(action.category)
            is ProductsContract.Action.LoadProductsBySubCategory -> loadProductsBySubCategory(action.subCategory)
            is ProductsContract.Action.ProductClicked -> navigateToProductDetails(action.product)
            is ProductsContract.Action.LoadProductsByBrand -> loadProductsByBrand(action.brand)
        }
    }

    private fun loadProductsByBrand(brand: Brand) {
        viewModelScope.launch(ioDispatcher) {
            productsUseCase.invokeProductsByBrand(brand)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _state.emit(
                                ProductsContract.State.ErrorByBrand(
                                    response.error?.localizedMessage ?: "Error",
                                    brand
                                )
                            )
                            Log.d("GTAG", "error: ${response.error}")
                        }

                        is ResultWrapper.Loading -> {
                            _state.emit(
                                ProductsContract.State.Loading(
                                    "Loading.."
                                )
                            )
                            Log.d("GTAG", "loading")
                        }

                        is ResultWrapper.ServerError -> {
                            _state.emit(
                                ProductsContract.State.ErrorByBrand(
                                    response.serverError.serverMessage,
                                    brand
                                )
                            )
                        }

                        is ResultWrapper.Success -> {
                            _state.emit(
                                ProductsContract.State.Success(
                                    response.data ?: listOf()
                                )
                            )
                            Log.d("GTAG", "success: ${response.data}")
                        }
                    }
                }

        }
    }

    private fun loadProductsByCategory(category: Category) {
        viewModelScope.launch(ioDispatcher) {
            productsUseCase.invokeProductsByCategory(category)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _state.emit(
                                ProductsContract.State.ErrorByCategory(
                                    response.error?.localizedMessage ?: "Error",
                                    category
                                )
                            )
                            Log.d("GTAG", "error: ${response.error}")
                        }

                        is ResultWrapper.Loading -> {
                            _state.emit(
                                ProductsContract.State.Loading(
                                    "Loading.."
                                )
                            )
                            Log.d("GTAG", "loading")
                        }

                        is ResultWrapper.ServerError -> {
                            _state.emit(
                                ProductsContract.State.ErrorByCategory(
                                    response.serverError.serverMessage,
                                    category
                                )
                            )
                        }

                        is ResultWrapper.Success -> {
                            _state.emit(
                                ProductsContract.State.Success(
                                    response.data ?: listOf()
                                )
                            )
                            Log.d("GTAG", "success: ${response.data}")
                        }
                    }
                }
        }
    }

    private fun navigateToProductDetails(product: Product) {
        _event.postValue(ProductsContract.Event.NavigateToProductDetails(product))
    }

    private fun loadProductsBySubCategory(subCategory: SubCategory) {
        viewModelScope.launch(ioDispatcher) {
            productsUseCase.invokeProductsBySubCategory(subCategory)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _state.emit(
                                ProductsContract.State.Error(
                                    response.error?.localizedMessage ?: "Error",
                                    subCategory
                                )
                            )
                            Log.d("GTAG", "error: ${response.error}")
                        }

                        is ResultWrapper.Loading -> {
                            _state.emit(
                                ProductsContract.State.Loading(
                                    "Loading.."
                                )
                            )
                            Log.d("GTAG", "loading")
                        }

                        is ResultWrapper.ServerError -> {
                            _state.emit(
                                ProductsContract.State.Error(
                                    response.serverError.serverMessage,
                                    subCategory
                                )
                            )
                        }

                        is ResultWrapper.Success -> {
                            _state.emit(
                                ProductsContract.State.Success(
                                    response.data ?: listOf()
                                )
                            )
                            Log.d("GTAG", "success: ${response.data}")
                        }
                    }
                }
        }
    }
}