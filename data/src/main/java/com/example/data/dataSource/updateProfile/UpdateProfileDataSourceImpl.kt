package com.example.data.dataSource.updateProfile

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.UpdateProfileDataSource
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.UpdateProfileRequest
import com.example.domain.model.user.UserResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateProfileDataSourceImpl @Inject constructor(
    private val webServices: WebServices
) : UpdateProfileDataSource {
    override suspend fun updateProfile(
        updateProfileRequest: UpdateProfileRequest,
        token: String
    )
            : Flow<ResultWrapper<UserResponse>> {
        return safeApiCall {
            webServices.updateProfile(updateProfileRequest, token)
                .toUserResponse()
        }
    }
}