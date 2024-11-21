package com.example.data.dataSourceContract

import com.example.domain.model.SignupRequest
import com.example.domain.model.user.UserResponse

interface SignupDataSource {
    suspend fun signup(signupRequest: SignupRequest): UserResponse
}