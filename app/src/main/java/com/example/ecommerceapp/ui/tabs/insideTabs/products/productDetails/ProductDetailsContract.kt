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
        class SuccessAddToWishlist(val product: Product, val message: String) : State()
        class SuccessAddToCart(val product: Product, val message: String) : State()
        data class Error(val message: String) : State()
    }

    sealed class Action {
        class AddToWishlistClicked(val product: Product) : Action()
        class AddToCartClicked(val product: Product) : Action()
        class UpdateQuantity(val product: Product, val quantity: Int) : Action()
    }

    sealed class Event
}