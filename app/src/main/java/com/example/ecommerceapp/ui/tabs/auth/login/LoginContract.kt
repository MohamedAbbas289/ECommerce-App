package com.example.ecommerceapp.ui.tabs.auth.login

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.StateFlow

class LoginContract {
    interface ViewModel {
        val states: StateFlow<State>
        val events: LiveData<Event>
        fun invokeAction(action: Action)
    }

    sealed class State {
        class Loading : State()
        class Success : State()
        class Error(val message: String) : State()
        class Nothing : State()
    }

    sealed class Action {
        class Login : Action()
    }

    sealed class Event {
        class NavigateToHome : Event()
    }

}