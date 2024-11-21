package com.example.data.repository.signup

import com.example.data.dataSourceContract.SignupDataSource
import com.example.domain.model.SignupRequest
import com.example.domain.model.user.UserResponse
import com.example.domain.repositories.signup.SignupRepository
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(
    private val signupDataSource: SignupDataSource
) : SignupRepository {
    override suspend fun signup(signupRequest: SignupRequest): UserResponse {
        return signupDataSource.signup(signupRequest)
    }
}