package com.example.ecommerceapp.ui.tabs.cart

import androidx.lifecycle.LiveData
import com.example.domain.model.cart.Cart
import kotlinx.coroutines.flow.StateFlow

class CartContract {
    interface ViewModel {
        val states: StateFlow<State>
        val events: LiveData<Event>
        fun invokeAction(action: Action)
    }

    sealed class State {
        class Error(val message: String) : State()
        class Success(val cart: Cart) : State()
        class Loading : State()
        class ErrorRemovingProduct(val message: String) : State()
        class SuccessRemovingProduct(val message: String, val cart: Cart) : State()
        class LoadingRemovingProduct : State()
        class ClearCartSuccess(val message: String) : State()
        class UpdateQuantitySuccess(val message: String, val cart: Cart) : State()
    }

    sealed class Action {
        class LoadCart : Action()
        class RemoveProductFromCart(val productId: String) : Action()
        class ClearCart : Action()
        class UpdateProductQuantity(val productId: String, val quantity: Int) : Action()
        class CheckoutClicked(val cartId: String) : Action()
    }

    sealed class Event {
        class NavigateToCheckout(val cartId: String) : Event()
    }
}