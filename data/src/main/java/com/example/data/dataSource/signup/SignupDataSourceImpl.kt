package com.example.data.dataSource.signup

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.SignupDataSource
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.SignupRequest
import com.example.domain.model.user.UserResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignupDataSourceImpl @Inject constructor(
    private val webServices: WebServices
) : SignupDataSource {
    override suspend fun signup(signupRequest: SignupRequest): Flow<ResultWrapper<UserResponse>> {
        val response = safeApiCall {
            webServices.signup(signupRequest)
                .toUserResponse()
        }
        return response
    }
}