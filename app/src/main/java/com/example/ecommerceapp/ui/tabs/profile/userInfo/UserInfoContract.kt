package com.example.ecommerceapp.ui.tabs.profile.userInfo

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.StateFlow


class UserInfoContract {
    interface ViewModel {
        val states: StateFlow<State>
        val events: LiveData<Event>
        fun invokeAction(action: Action)
    }

    sealed class State {
        class Success : State()
        class UpdateUserSuccess(val message: String) : State()
        class Error(val message: String) : State()
        class UpdateUserError(val message: String) : State()
        class Loading : State()
        class Initial : State()
    }

    sealed class Action {
        class GetUser : Action()
        class UpdateUser(val userName: String, val email: String, val phone: String) : Action()
    }

    sealed class Event {
        class NavigateToProfile : Event()
    }

}