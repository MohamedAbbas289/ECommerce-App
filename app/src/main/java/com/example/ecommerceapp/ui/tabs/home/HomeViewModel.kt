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
import kotlinx.coroutines.async
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
        HomeContract.State.Initial()
    )
    override val states = _state

    private val _event = MutableLiveData<HomeContract.Event>()
    override val events = _event

    override fun invokeAction(action: HomeContract.Action) {
        when (action) {
            is HomeContract.Action.ProductClicked -> navigateToProductDetails(action.product)
            is HomeContract.Action.LoadAllData -> loadAllData(action.subCategory)
        }
    }

    private fun loadAllData(subCategory: SubCategory) {
        viewModelScope.launch(ioDispatcher) {
            _state.emit(HomeContract.State.Loading())
            val categoriesDeferred = async { getCategoriesUseCase.invoke() }
            val brandsDeferred = async { getBrandsUseCase.invoke() }
            val productsDeferred =
                async { getProductsUseCase.invokeProductsBySubCategory(subCategory) }
            try {
                categoriesDeferred.await().collect { response ->
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
                            //_state.emit(HomeContract.State.LoadingByCategories())
                        }
                    }
                }

                brandsDeferred.await().collect { response ->
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
                            _state.emit(HomeContract.State.LoadingByBrands())
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
                            Log.d("GTAG", "success")
                        }
                    }
                }

                productsDeferred.await().collect { response ->
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
                            _state.emit(HomeContract.State.LoadingByProducts())
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
                            Log.d("GTAG", "success")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("GTAG", " async error: ${e.message}")
            }
        }
    }

    private fun navigateToProductDetails(product: Product) {
        _event.postValue(HomeContract.Event.NavigateToProductsDetails(product))
    }


}
