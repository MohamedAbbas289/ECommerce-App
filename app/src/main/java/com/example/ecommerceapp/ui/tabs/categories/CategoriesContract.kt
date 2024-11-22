package com.example.ecommerceapp.ui.tabs.categories

import androidx.lifecycle.LiveData
import com.example.domain.model.Category
import com.example.domain.model.SubCategory
import kotlinx.coroutines.flow.StateFlow


class CategoriesContract {

    interface ViewModel {
        val states: StateFlow<State>
        val events: LiveData<Event>
        fun invokeAction(action: Action)
    }

    sealed class State {
        class ErrorByCategory(val message: String) : State()
        class SuccessByCategory(val categories: List<Category?>) : State()
        data object LoadingByCategory : State()
        class SuccessBySubCategory(val subCategories: List<SubCategory?>) : State()
        class ErrorBySubCategory(val message: String, val category: Category) : State()
        class LoadingBySubCategory(val message: String) : State()
    }

    sealed class Action {
        object LoadCategories : Action()
        class CategoryClicked(val category: Category) : Action()
        class SubCategoryClicked(val subCategory: SubCategory) : Action()
    }

    sealed class Event {
        class NavigateToSubCategories(val cat: Category) : Event()
        class NavigateToProducts(val subCategory: SubCategory) : Event()
    }
}