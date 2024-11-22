package com.example.data.dataSourceContract

import com.example.domain.common.ResultWrapper
import com.example.domain.model.SignupRequest
import com.example.domain.model.user.UserResponse
import kotlinx.coroutines.flow.Flow

interface SignupDataSource {
    suspend fun signup(signupRequest: SignupRequest): Flow<ResultWrapper<UserResponse>>
}