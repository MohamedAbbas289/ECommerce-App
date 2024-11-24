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
        class ErrorByCategories(val message: String) : State()
        class ErrorByProducts(val message: String) : State()
        class ErrorByBrands(val message: String) : State()
        class Success(val categories: List<Category?>) : State()
        class SuccessByProducts(val products: List<Product?>) : State()
        class SuccessByBrands(val brands: List<Brand?>) : State()
        class LoadingByCategories : State()
        class LoadingByProducts : State()
        class LoadingByBrands : State()
        class Initial : State()
    }

    sealed class Action {
        object LoadCategories : Action()
        object LoadBrands : Action()
        class LoadProducts(val subCategory: SubCategory) : Action()
        class ProductClicked(val product: Product) : Action()
    }

    sealed class Event {
        class NavigateToProductsDetails(val product: Product) : Event()
    }
}