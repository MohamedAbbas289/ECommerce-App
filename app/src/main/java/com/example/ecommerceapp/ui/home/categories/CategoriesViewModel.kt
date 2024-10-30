package com.example.ecommerceapp.ui.home.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Category
import com.example.domain.usecase.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoriesUseCase: GetCategoriesUseCase
) : ViewModel(), CategoriesContract.ViewModel {
    private val _state = MutableLiveData<CategoriesContract.State>()
    override val states = _state

    private val _event = MutableLiveData<CategoriesContract.Event>()
    override val events = _event


    override fun invokeAction(action: CategoriesContract.Action) {
        when (action) {
            is CategoriesContract.Action.LoadCategories -> loadCategories()
            is CategoriesContract.Action.CategoryClicked -> {

            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                _state.postValue(CategoriesContract.State.Loading(message = "Loading"))
                val data = categoriesUseCase.invoke()
                _state.postValue(CategoriesContract.State.Success(data ?: listOf()))
            } catch (e: Exception) {
                _state.postValue(
                    CategoriesContract.State.Error(
                        message = e.localizedMessage ?: "Error"
                    )
                )
            }
        }
    }

}