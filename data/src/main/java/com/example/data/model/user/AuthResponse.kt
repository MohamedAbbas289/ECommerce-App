package com.example.data.model.user

import com.example.domain.model.user.Errors
import com.example.domain.model.user.UserResponse
import com.google.gson.annotations.SerializedName

data class AuthResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("user")
    val user: UserDto? = null,

    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("errors")
    val errors: Errors? = null
) {
    fun toUserResponse(): UserResponse {
        return UserResponse(
            message = message,
            user = user?.toUser(),
            token = token,
            errors = errors
        )
    }
}