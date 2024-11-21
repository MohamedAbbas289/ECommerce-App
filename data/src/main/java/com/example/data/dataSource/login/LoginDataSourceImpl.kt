package com.example.data.dataSource.login

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.LoginDataSource
import com.example.domain.model.LoginRequest
import com.example.domain.model.user.UserResponse
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val webServices: WebServices
) : LoginDataSource {
    override suspend fun login(request: LoginRequest): UserResponse {
        val response = webServices.login(request)
        return response.toUserResponse()
    }

}