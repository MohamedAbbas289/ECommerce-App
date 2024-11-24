package com.example.ecommerceapp.ui.tabs.auth.signup

import androidx.lifecycle.LiveData
import com.example.domain.model.user.UserResponse
import kotlinx.coroutines.flow.StateFlow

class RegisterContract {
    interface ViewModel {
        val states: StateFlow<State>
        val events: LiveData<Event>
        fun invokeAction(action: Action)
    }

    sealed class State {
        class Error(val message: String) : State()
        class Success(val userResponse: UserResponse) : State()
        class Loading : State()
        class Nothing : State()
    }

    sealed class Action {
        class Register : Action()
    }

    sealed class Event {
        class NavigateToHome : Event()
    }
}