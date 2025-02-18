package com.example.ecommerceapp.ui.tabs.home

import androidx.lifecycle.LiveData
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.SubCategory


class HomeContract {
    interface ViewModel {
        val states: LiveData<State>
        val events: LiveData<Event>
        fun invokeAction(action: Action)
    }

    sealed class State {
        class Error(val message: String) : State()
        class Success(val categories: List<Category?>) : State()
        class SuccessByProducts(val products: List<Product?>) : State()
        class Loading : State()
    }

    sealed class Action {
        object LoadCategories : Action()
        class LoadProducts(val subCategory: SubCategory) : Action()
        class ProductClicked(val product: Product) : Action()
    }

    sealed class Event {
        class NavigateToProductsDetails(val product: Product) : Event()
    }
}