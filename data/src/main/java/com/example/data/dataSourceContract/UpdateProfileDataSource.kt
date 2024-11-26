package com.example.data.dataSourceContract

import com.example.domain.common.ResultWrapper
import com.example.domain.model.UpdateProfileRequest
import com.example.domain.model.user.UserResponse
import kotlinx.coroutines.flow.Flow

interface UpdateProfileDataSource {
    suspend fun updateProfile(
        updateProfileRequest: UpdateProfileRequest,
        token: String
    ): Flow<ResultWrapper<UserResponse>>
}