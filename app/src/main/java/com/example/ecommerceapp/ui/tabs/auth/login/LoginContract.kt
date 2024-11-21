package com.example.ecommerceapp.ui.tabs.auth.login

import androidx.lifecycle.LiveData
import com.example.domain.model.LoginRequest
import com.example.domain.model.user.UserResponse

class LoginContract {
    interface ViewModel {
        val states: LiveData<State>
        val events: LiveData<Event>
        fun invokeAction(action: Action)
    }

    sealed class State {
        class Loading : State()
        class Success : State()
        class Error(val message: String) : State()
    }

    sealed class Action {
        class Login : Action()
    }

    sealed class Event {
        class NavigateToHome : Event()
    }

}