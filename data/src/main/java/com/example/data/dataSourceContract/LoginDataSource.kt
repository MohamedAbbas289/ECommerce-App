package com.example.data.dataSourceContract

import com.example.domain.model.LoginRequest
import com.example.domain.model.user.UserResponse

interface LoginDataSource {
    suspend fun login(request: LoginRequest): UserResponse
}