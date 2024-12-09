package com.example.ecommerceapp.ui.tabs.home

import androidx.lifecycle.LiveData
import com.example.domain.model.Brand
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.SubCategory
import kotlinx.coroutines.flow.StateFlow


class HomeContract {
    interface ViewModel {
        val states: StateFlow<State>
        val events: LiveData<Event>
        fun invokeAction(action: Action)
    }

    sealed class State {
        class Error(val message: String) : State()
        class Success(val categories: List<Category?>) : State()
        class SuccessByProducts(val products: List<Product?>) : State()
        class SuccessByBrands(val brands: List<Brand?>) : State()
        class Loading : State()
        class LoadingByProducts : State()
        class LoadingByBrands : State()
        class LoadingAdd : State()
        class SuccessAdd(val product: Product, val message: String) : State()
        class ErrorAdd(val message: String) : State()
        class Initial : State()
    }

    sealed class Action {
        class LoadAllData(val subCategory: SubCategory) : Action()
        class ProductClicked(val product: Product) : Action()
        class AddToWishlistClicked(val product: Product) : Action()
        class AddToCartClicked(val product: Product) : Action()
    }

    sealed class Event {
        class NavigateToProductsDetails(val product: Product) : Event()
    }
}