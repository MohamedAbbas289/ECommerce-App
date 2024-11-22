package com.example.domain.repositories.signup

import com.example.domain.common.ResultWrapper
import com.example.domain.model.SignupRequest
import com.example.domain.model.user.UserResponse
import kotlinx.coroutines.flow.Flow

interface SignupRepository {
    suspend fun signup(signupRequest: SignupRequest): Flow<ResultWrapper<UserResponse>>
}