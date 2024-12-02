package com.example.ecommerceapp.ui.tabs.insideTabs.products.productDetails

import androidx.lifecycle.LiveData
import com.example.domain.model.Product
import kotlinx.coroutines.flow.StateFlow

class ProductDetailsContract {
    interface ViewModel {
        val states: StateFlow<State>
        val events: LiveData<Event>
        fun invokeAction(action: Action)
    }

    sealed class State {
        class Initial : State()
        class Loading : State()
        class AddToWishlistSuccess(val product: Product, val message: String) : State()
        data class Error(val message: String) : State()
    }

    sealed class Action {
        class AddToWishlistClicked(val product: Product) : Action()
    }

    sealed class Event
}