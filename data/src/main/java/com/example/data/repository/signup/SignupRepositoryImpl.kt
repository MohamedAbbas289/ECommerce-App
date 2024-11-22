package com.example.data.repository.signup

import com.example.data.dataSourceContract.SignupDataSource
import com.example.domain.common.ResultWrapper
import com.example.domain.model.SignupRequest
import com.example.domain.model.user.UserResponse
import com.example.domain.repositories.signup.SignupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(
    private val signupDataSource: SignupDataSource
) : SignupRepository {
    override suspend fun signup(signupRequest: SignupRequest): Flow<ResultWrapper<UserResponse>> {
        return signupDataSource.signup(signupRequest)
    }
}