package com.example.domain.usecase

import com.example.domain.model.SignupRequest
import com.example.domain.repositories.signup.SignupRepository
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val signupRepository: SignupRepository
) {
    suspend fun invoke(signupRequest: SignupRequest) = signupRepository.signup(signupRequest)
}