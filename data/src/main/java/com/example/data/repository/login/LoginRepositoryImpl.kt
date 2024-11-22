package com.example.data.repository.login

import com.example.data.dataSourceContract.LoginDataSource
import com.example.domain.common.ResultWrapper
import com.example.domain.model.LoginRequest
import com.example.domain.model.user.UserResponse
import com.example.domain.repositories.login.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
) : LoginRepository {
    override suspend fun login(request: LoginRequest): Flow<ResultWrapper<UserResponse>> {
        return loginDataSource.login(request)
    }
}