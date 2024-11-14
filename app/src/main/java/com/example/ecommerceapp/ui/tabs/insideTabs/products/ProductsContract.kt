package com.example.ecommerceapp.ui.tabs.insideTabs.products

import androidx.lifecycle.LiveData
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.SubCategory

class ProductsContract {
    interface ViewModel {
        val states: LiveData<State>
        val events: LiveData<Event>
        fun invokeAction(action: Action)
    }

    sealed class State {
        class Success(val products: List<Product?>) : State()
        class Error(val message: String, val subCategory: SubCategory) : State()
        class ErrorByCategory(val message: String, val category: Category) : State()
        class Loading(val message: String) : State()
    }

    sealed class Action {
        class LoadProductsByCategory(val category: Category) : Action()
        class LoadProductsBySubCategory(val subCategory: SubCategory) : Action()
        class ProductClicked(val product: Product) : Action()

    }

    sealed class Event {
        class NavigateToProductDetails(val product: Product) : Event()
    }
}