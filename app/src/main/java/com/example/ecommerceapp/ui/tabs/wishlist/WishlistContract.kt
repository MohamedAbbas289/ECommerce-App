package com.example.ecommerceapp.ui.tabs.wishlist

import androidx.lifecycle.LiveData
import com.example.domain.model.Product
import kotlinx.coroutines.flow.StateFlow

class WishlistContract {
    interface ViewModel {
        val states: StateFlow<State>
        val events: LiveData<Event>
        fun invokeAction(action: Action)
    }

    sealed class State {
        class Error(val message: String) : State()
        class Success(val wishlistProducts: List<Product?>?) : State()
        class Loading : State()
        class RemoveFromWishlistSuccess : State()
        class RemoveFromWishlistLoading : State()
        class RemoveFromWishlistError(val message: String) : State()
    }

    sealed class Action {
        class LoadWishlist : Action()
        class RemoveFromWishlist(val productId: String) : Action()
    }

    sealed class Event
}