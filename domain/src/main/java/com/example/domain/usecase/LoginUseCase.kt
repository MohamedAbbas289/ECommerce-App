package com.example.domain.usecase

import com.example.domain.model.LoginRequest
import com.example.domain.repositories.login.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend fun invoke(request: LoginRequest) =
        loginRepository.login(request)
}