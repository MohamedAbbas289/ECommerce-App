package com.example.domain.usecase

import com.example.domain.model.UpdateProfileRequest
import com.example.domain.repositories.updateProfile.UpdateProfileRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val updateProfileRepository: UpdateProfileRepository
) {
    suspend fun invoke(
        updateProfileRequest: UpdateProfileRequest,
        token: String
    ) =
        updateProfileRepository.updateProfile(updateProfileRequest, token)

}