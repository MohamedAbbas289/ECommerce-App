package com.example.ecommerceapp.ui.tabs.categories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.model.SubCategory
import com.example.domain.usecase.GetCategoriesUseCase
import com.example.domain.usecase.GetSubCategoriesUseCase
import com.example.ecommerceapp.utils.DispatchersModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoriesUseCase: GetCategoriesUseCase,
    private val subCategoriesUseCase: GetSubCategoriesUseCase,
    @DispatchersModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel(), CategoriesContract.ViewModel {
    private val _state = MutableStateFlow<CategoriesContract.State>(
        CategoriesContract.State.LoadingByCategory
    )
    override val states = _state

    private val _event = MutableLiveData<CategoriesContract.Event>()
    override val events = _event

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    override fun invokeAction(action: CategoriesContract.Action) {
        when (action) {
            is CategoriesContract.Action.LoadCategories -> loadCategories()
            is CategoriesContract.Action.CategoryClicked -> loadSubCategories(action.category)
            is CategoriesContract.Action.SubCategoryClicked -> navigateToProducts(action.subCategory)
        }
    }

    private fun navigateToProducts(subCategory: SubCategory) {
        _event.postValue(CategoriesContract.Event.NavigateToProducts(subCategory))
    }

    private fun loadSubCategories(category: Category) {
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            _state.emit(CategoriesContract.State.LoadingBySubCategory(message = "Loading"))
            subCategoriesUseCase.invoke(category)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Success -> {
                            _state.emit(
                                CategoriesContract.State.SuccessBySubCategory(
                                    response.data ?: listOf()
                                )
                            )
                            Log.d("GTAG", "success: ${response.data}")
                        }
                        is ResultWrapper.Error -> {
                            _state.emit(
                                CategoriesContract.State.ErrorBySubCategory(
                                    response.error?.localizedMessage ?: "Error",
                                    category
                                )
                            )
                            Log.d("GTAG", "error: ${response.error}")
                        }

                        is ResultWrapper.ServerError -> {
                            _state.emit(
                                CategoriesContract.State.ErrorBySubCategory(
                                    response.serverError.serverMessage,
                                    category
                                )
                            )
                            Log.d("GTAG", "serverError: ${response.serverError}")
                        }

                        is ResultWrapper.Loading -> {
                            _state.emit(
                                CategoriesContract.State.LoadingBySubCategory(
                                    "Loading.."
                                )
                            )
                            Log.d("GTAG", "loading")
                        }


                    }
                }

        }
    }

    private fun loadCategories() {
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            categoriesUseCase.invoke()
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Success -> {
                            _state.emit(
                                CategoriesContract.State.SuccessByCategory(
                                    response.data ?: listOf()
                                )
                            )
                            Log.d("GTAG", "success")
                        }

                        is ResultWrapper.Error -> {
                            _state.emit(
                                CategoriesContract.State.ErrorByCategory(
                                    response.error?.localizedMessage ?: "Error"
                                )
                            )
                            Log.d("GTAG", "error: ${response.error}")
                        }

                        is ResultWrapper.ServerError -> {
                            _state.emit(
                                CategoriesContract.State.ErrorByCategory(
                                    response.serverError.serverMessage
                                )
                            )
                            Log.d("GTAG", "serverError: ${response.serverError}")
                        }

                        is ResultWrapper.Loading -> {
                            _state.emit(CategoriesContract.State.LoadingByCategory)
                            Log.d("GTAG", "loading")
                        }

                    }
                }


        }
    }

}