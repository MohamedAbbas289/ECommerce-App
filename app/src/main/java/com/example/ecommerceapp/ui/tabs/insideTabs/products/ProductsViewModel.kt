package com.example.ecommerceapp.ui.tabs.insideTabs.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Brand
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.SubCategory
import com.example.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productsUseCase: GetProductsUseCase
) : ViewModel(), ProductsContract.ViewModel {
    private val _state = MutableLiveData<ProductsContract.State>()
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
        viewModelScope.launch {
            try {
                _state.postValue(ProductsContract.State.Loading(message = "Loading"))
                val data = productsUseCase.invokeProductsByBrand(brand)
                _state.postValue(ProductsContract.State.Success(data ?: listOf()))
            } catch (e: Exception) {
                _state.postValue(
                    ProductsContract.State.ErrorByBrand(
                        message = e.localizedMessage ?: "Error",
                        brand = brand
                    )
                )
            }
        }
    }

    private fun loadProductsByCategory(category: Category) {
        viewModelScope.launch {
            try {
                _state.postValue(ProductsContract.State.Loading(message = "Loading"))
                val data = productsUseCase.invokeProductsByCategory(category)
                _state.postValue(ProductsContract.State.Success(data ?: listOf()))
            } catch (e: Exception) {
                _state.postValue(
                    ProductsContract.State.ErrorByCategory(
                        message = e.localizedMessage ?: "Error",
                        category = category
                    )
                )
            }
        }
    }

    private fun navigateToProductDetails(product: Product) {
        _event.postValue(ProductsContract.Event.NavigateToProductDetails(product))
    }

    private fun loadProductsBySubCategory(subCategory: SubCategory) {
        viewModelScope.launch {
            try {
                _state.postValue(ProductsContract.State.Loading(message = "Loading"))
                val data = productsUseCase.invokeProductsBySubCategory(subCategory)
                _state.postValue(ProductsContract.State.Success(data ?: listOf()))
            } catch (e: Exception) {
                _state.postValue(
                    ProductsContract.State.Error(
                        message = e.localizedMessage ?: "Error",
                        subCategory = subCategory
                    )
                )
            }
        }
    }
}