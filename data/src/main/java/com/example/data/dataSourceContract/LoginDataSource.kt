package com.example.data.dataSourceContract

import com.example.domain.common.ResultWrapper
import com.example.domain.model.LoginRequest
import com.example.domain.model.user.UserResponse
import kotlinx.coroutines.flow.Flow

interface LoginDataSource {
    suspend fun login(request: LoginRequest): Flow<ResultWrapper<UserResponse>>
}