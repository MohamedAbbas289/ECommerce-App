package com.example.ecommerceapp.ui.home.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Category
import com.example.domain.usecase.GetCategoriesUseCase
import com.example.domain.usecase.GetSubCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoriesUseCase: GetCategoriesUseCase,
    private val subCategoriesUseCase: GetSubCategoriesUseCase
) : ViewModel(), CategoriesContract.ViewModel {
    private val _state = MutableLiveData<CategoriesContract.State>()
    override val states = _state

    private val _event = MutableLiveData<CategoriesContract.Event>()
    override val events = _event


    override fun invokeAction(action: CategoriesContract.Action) {
        when (action) {
            is CategoriesContract.Action.LoadCategories -> loadCategories()
            is CategoriesContract.Action.CategoryClicked -> loadSubCategories(action.category)
        }
    }

    private fun loadSubCategories(category: Category) {
        viewModelScope.launch {
            try {
                _state.postValue(CategoriesContract.State.LoadingBySubCategory(message = "Loading"))
                val data = subCategoriesUseCase.invoke(category)
                _state.postValue(CategoriesContract.State.SuccessBySubCategory(data ?: listOf()))
            } catch (e: Exception) {
                _state.postValue(
                    CategoriesContract.State.ErrorBySubCategory(
                        message = e.localizedMessage ?: "Error"
                    )
                )
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                _state.postValue(CategoriesContract.State.LoadingByCategory(message = "Loading"))
                val data = categoriesUseCase.invoke()
                _state.postValue(CategoriesContract.State.SuccessByCategory(data ?: listOf()))
            } catch (e: Exception) {
                _state.postValue(
                    CategoriesContract.State.ErrorByCategory(
                        message = e.localizedMessage ?: "Error"
                    )
                )
            }
        }
    }

}