package com.example.domain.usecase

import com.example.domain.model.user.UserResponse
import com.example.domain.repositories.sessionManager.SessionManagerRepository
import javax.inject.Inject

class SaveSessionUseCase @Inject constructor(
    private val sessionManagerRepository: SessionManagerRepository
) {
    operator fun invoke(userResponse: UserResponse) =
        sessionManagerRepository.saveUserData(userResponse)
}