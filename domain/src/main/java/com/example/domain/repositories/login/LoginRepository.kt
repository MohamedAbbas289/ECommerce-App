package com.example.domain.repositories.login

import com.example.domain.model.LoginRequest
import com.example.domain.model.user.UserResponse

interface LoginRepository {
    suspend fun login(request: LoginRequest): UserResponse
}