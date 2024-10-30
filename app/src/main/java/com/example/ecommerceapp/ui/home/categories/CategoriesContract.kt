package com.example.ecommerceapp.ui.home.categories

import androidx.lifecycle.LiveData
import com.example.domain.model.Category


class CategoriesContract {

    interface ViewModel {
        val states: LiveData<State>
        val events: LiveData<Event>
        fun invokeAction(action: Action)
    }

    sealed class State {
        class Error(val message: String) : State()
        class Success(val categories: List<Category?>) : State()
        class Loading(val message: String) : State()
    }

    sealed class Action {
        object LoadCategories : Action()
        class CategoryClicked(val category: Category) : Action()
    }

    sealed class Event {
        class NavigateToSubCategories(cat: Category) : Event()
    }
}