package com.example.domain.usecase

import com.example.domain.repositories.sessionManager.SessionManagerRepository
import javax.inject.Inject

class GetSessionUserUseCase @Inject constructor(
    private val sessionManagerRepository: SessionManagerRepository
) {
    operator fun invoke() = sessionManagerRepository.getUserData()
}