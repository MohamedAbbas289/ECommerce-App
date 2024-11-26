package com.example.ecommerceapp.ui.tabs.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    fun logout() {
        _isLoading.value = true
        logoutUseCase()
    }

}