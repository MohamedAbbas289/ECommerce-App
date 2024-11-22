package com.example.ecommerceapp.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetSessionUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getSessionUserUseCase: GetSessionUserUseCase
) : ViewModel() {

    sealed class Destination {
        data object HOME : Destination()
        data object LOGIN : Destination()
    }

    private val _navigateTo = MutableLiveData<Destination>()
    val navigateTo: LiveData<Destination> get() = _navigateTo

    fun checkUserSession() {
        viewModelScope.launch {
            val user = getSessionUserUseCase()
            if (user?.token != null) {
                // User is logged in
                _navigateTo.value = Destination.HOME
            } else {
                // No user session, go to login
                _navigateTo.value = Destination.LOGIN
            }
        }
    }
}
