package com.example.data.dataSource.signup

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.SignupDataSource
import com.example.domain.model.SignupRequest
import com.example.domain.model.user.UserResponse
import javax.inject.Inject

class SignupDataSourceImpl @Inject constructor(
    private val webServices: WebServices
) : SignupDataSource {
    override suspend fun signup(signupRequest: SignupRequest): UserResponse {
        val response = webServices.signup(signupRequest)
        return response.toUserResponse()
    }
}