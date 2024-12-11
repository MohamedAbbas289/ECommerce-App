package com.example.ecommerceapp.ui.tabs.profile.addresses

import androidx.lifecycle.LiveData
import com.example.domain.model.addresses.Address
import kotlinx.coroutines.flow.StateFlow

class AddressesContract {
    interface ViewModel {
        val states: StateFlow<State>
        val events: LiveData<Event>
        fun invokeAction(action: Action)
    }

    sealed class State {
        class Success(val addresses: List<Address?>?) : State()
        class Error(val message: String) : State()
        class Loading : State()
        class Initial : State()
        class SuccessAdd(val message: String, val addresses: List<Address?>?) : State()
        class ErrorAdd(val message: String) : State()
        class LoadingAdd : State()
        class SuccessRemove(val message: String) : State()
        class ErrorRemove(val message: String) : State()
        class LoadingRemove : State()
    }

    sealed class Action {
        class AddAddress : Action()
        class LoadAddresses : Action()
        class RemoveAddress(val addressId: String) : Action()
    }

    sealed class Event
}