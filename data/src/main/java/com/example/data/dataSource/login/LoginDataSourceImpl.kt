package com.example.data.dataSource.login

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.LoginDataSource
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.LoginRequest
import com.example.domain.model.user.UserResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val webServices: WebServices
) : LoginDataSource {
    override suspend fun login(request: LoginRequest): Flow<ResultWrapper<UserResponse>> {
        val response = safeApiCall {
            webServices.login(request).toUserResponse()
        }
        return response
    }



}