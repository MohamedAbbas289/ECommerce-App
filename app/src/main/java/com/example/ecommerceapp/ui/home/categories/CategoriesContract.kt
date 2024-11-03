package com.example.ecommerceapp.ui.home.categories

import androidx.lifecycle.LiveData
import com.example.domain.model.Category
import com.example.domain.model.SubCategory


class CategoriesContract {

    interface ViewModel {
        val states: LiveData<State>
        val events: LiveData<Event>
        fun invokeAction(action: Action)
    }

    sealed class State {
        class ErrorByCategory(val message: String) : State()
        class SuccessByCategory(val categories: List<Category?>) : State()
        class LoadingByCategory(val message: String) : State()
        class SuccessBySubCategory(val subCategories: List<SubCategory?>) : State()
        class ErrorBySubCategory(val message: String) : State()
        class LoadingBySubCategory(val message: String) : State()
    }

    sealed class Action {
        object LoadCategories : Action()
        class CategoryClicked(val category: Category) : Action()
    }

    sealed class Event {
        class NavigateToSubCategories(val cat: Category) : Event()
    }
}