package com.example.domain.repositories.sessionManager

import com.example.domain.model.user.UserResponse

interface SessionManagerRepository {
    fun saveUserData(userResponse: UserResponse)
    fun getUserData(): UserResponse?
    fun logout()
}