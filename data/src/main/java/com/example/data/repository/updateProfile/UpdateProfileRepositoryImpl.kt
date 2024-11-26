package com.example.data.repository.updateProfile

import com.example.data.dataSourceContract.UpdateProfileDataSource
import com.example.domain.common.ResultWrapper
import com.example.domain.model.UpdateProfileRequest
import com.example.domain.model.user.UserResponse
import com.example.domain.repositories.updateProfile.UpdateProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateProfileRepositoryImpl @Inject constructor(
    private val updateProfileDataSource: UpdateProfileDataSource
) : UpdateProfileRepository {
    override suspend fun updateProfile(
        updateProfileRequest: UpdateProfileRequest,
        token: String
    )
            : Flow<ResultWrapper<UserResponse>> {
        return updateProfileDataSource.updateProfile(updateProfileRequest, token)
    }
}