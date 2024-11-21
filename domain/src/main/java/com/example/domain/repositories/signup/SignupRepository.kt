package com.example.domain.repositories.signup

import com.example.domain.model.SignupRequest
import com.example.domain.model.user.UserResponse

interface SignupRepository {
    suspend fun signup(signupRequest: SignupRequest): UserResponse
}