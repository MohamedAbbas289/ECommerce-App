package com.example.ecommerceapp.ui.tabs.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import com.example.domain.model.SubCategory
import com.example.domain.usecase.GetBrandsUseCase
import com.example.domain.usecase.GetCategoriesUseCase
import com.example.domain.usecase.GetProductsUseCase
import com.example.ecommerceapp.utils.DispatchersModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val getBrandsUseCase: GetBrandsUseCase,
    @DispatchersModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel(), HomeContract.ViewModel {
    private val _state = MutableStateFlow<HomeContract.State>(
        HomeContract.State.Loading()
    )
    override val states = _state

    private val _event = MutableLiveData<HomeContract.Event>()
    override val events = _event

    override fun invokeAction(action: HomeContract.Action) {
        when (action) {
            HomeContract.Action.LoadCategories -> loadCategories()
            is HomeContract.Action.LoadProducts -> loadProducts(action.subCategory)
            is HomeContract.Action.ProductClicked -> navigateToProductDetails(action.product)
            HomeContract.Action.LoadBrands -> loadBrands()
        }
    }

    private fun loadBrands() {
        viewModelScope.launch(ioDispatcher) {
            getBrandsUseCase.invoke()
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _state.emit(
                                HomeContract.State.Error(
                                    response.error?.message ?: "Error"
                                )
                            )
                            Log.d("GTAG", "error: ${response.error?.message}")
                        }

                        is ResultWrapper.Loading -> {
                            _state.emit(HomeContract.State.Loading())
                            Log.d("GTAG", "loading")
                        }

                        is ResultWrapper.ServerError -> {
                            _state.emit(
                                HomeContract.State.Error(
                                    response.serverError.serverMessage
                                )
                            )
                            Log.d("GTAG", "server error: ${response.serverError.serverMessage}")
                        }

                        is ResultWrapper.Success -> {
                            _state.emit(
                                HomeContract.State.SuccessByBrands(
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
        _event.postValue(HomeContract.Event.NavigateToProductsDetails(product))
    }

    private fun loadProducts(subCategory: SubCategory) {
        viewModelScope.launch(ioDispatcher) {
            getProductsUseCase.invokeProductsBySubCategory(subCategory)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _state.emit(
                                HomeContract.State.Error(
                                    response.error?.localizedMessage ?: "Error"
                                )
                            )
                            Log.d("GTAG", "error: ${response.error?.localizedMessage}")
                        }

                        is ResultWrapper.Loading -> {
                            _state.emit(HomeContract.State.Loading())
                            Log.d("GTAG", "loading: ")
                        }

                        is ResultWrapper.ServerError -> {
                            _state.emit(
                                HomeContract.State.Error(
                                    response.serverError.serverMessage
                                )
                            )
                            Log.d("GTAG", "server error: ${response.serverError.serverMessage}")
                        }

                        is ResultWrapper.Success -> {
                            _state.emit(
                                HomeContract.State.SuccessByProducts(
                                    response.data ?: listOf()
                                )
                            )
                            Log.d("GTAG", "success: ${response.data}")
                        }
                    }

                }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch(ioDispatcher) {
            getCategoriesUseCase.invoke()
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _state.emit(
                                HomeContract.State.Error(
                                    response.error?.localizedMessage ?: "Error"
                                )
                            )
                        }

                        is ResultWrapper.ServerError -> {
                            _state.emit(
                                HomeContract.State.Error(
                                    response.serverError.serverMessage
                                )
                            )
                        }

                        is ResultWrapper.Success -> {
                            _state.emit(
                                HomeContract.State.Success(
                                    response.data ?: listOf()
                                )
                            )
                        }

                        is ResultWrapper.Loading -> {
                            _state.emit(HomeContract.State.Loading())
                        }
                    }

                }

        }
    }
}
