package com.example.ecommerceapp.ui.tabs.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Product
import com.example.domain.model.SubCategory
import com.example.domain.usecase.GetCategoriesUseCase
import com.example.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel(), HomeContract.ViewModel {
    private val _state = MutableLiveData<HomeContract.State>()
    override val states = _state

    private val _event = MutableLiveData<HomeContract.Event>()
    override val events = _event

    override fun invokeAction(action: HomeContract.Action) {
        when (action) {
            HomeContract.Action.LoadCategories -> loadCategories()
            is HomeContract.Action.LoadProducts -> loadProducts(action.subCategory)
            is HomeContract.Action.ProductClicked -> navigateToProductDetails(action.product)
        }
    }

    private fun navigateToProductDetails(product: Product) {
        _event.postValue(HomeContract.Event.NavigateToProductsDetails(product))
    }

    private fun loadProducts(subCategory: SubCategory) {
        viewModelScope.launch {
            try {
                val data = getProductsUseCase.invokeProductsBySubCategory(subCategory)
                _state.postValue(HomeContract.State.SuccessByProducts(data ?: listOf()))
            } catch (e: Exception) {
                _state.postValue(
                    HomeContract.State.Error(
                        message = e.localizedMessage ?: "Error"
                    )
                )
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                _state.postValue(HomeContract.State.Loading())
                val data = getCategoriesUseCase.invoke()
                _state.postValue(HomeContract.State.Success(data ?: listOf()))
            } catch (e: Exception) {
                _state.postValue(
                    HomeContract.State.Error(
                        message = e.localizedMessage ?: "Error"
                    )
                )
            }
        }
    }
}