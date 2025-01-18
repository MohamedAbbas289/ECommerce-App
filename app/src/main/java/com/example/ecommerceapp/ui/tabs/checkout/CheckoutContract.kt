package com.example.ecommerceapp.ui.tabs.checkout

import androidx.lifecycle.LiveData
import com.example.domain.model.OrderRequest
import com.example.domain.model.addresses.Address
import com.example.domain.model.order.OrderDetails
import com.example.domain.model.order.Session
import kotlinx.coroutines.flow.StateFlow

class CheckoutContract {
    interface ViewModel {
        val state: StateFlow<State>
        val event: LiveData<Event>
        fun invokeAction(action: Action)
    }

    sealed class State {
        class Error(val message: String) : State()
        class Success(val addresses: List<Address?>) : State()
        class Loading : State()
        object Initial : State()
        class SuccessOrder : State()
        class SuccessOnlineOrder(val session: Session) : State()
        class ErrorOrder(val message: String) : State()
        class LoadingOrder : State()
    }

    sealed class Action {
        class LoadUserAddresses : Action()
        class CreateCashOrder(val cartId: String, val orderRequest: OrderRequest) : Action()
        class CreateCreditCardOrder(val cartId: String, val orderRequest: OrderRequest) : Action()
    }

    sealed class Event {
        class NavigateToOrderCompleteScreen(val orderDetails: OrderDetails) : Event()
    }
}