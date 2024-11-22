package com.example.data.model.user

import com.example.domain.model.user.User
import com.google.gson.annotations.SerializedName

data class UserDto(

    @field:SerializedName("role")
    val role: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("email")
    val email: String? = null
) {
    fun toUser(): User {
        return User(
            role = role,
            name = name,
            email = email
        )
    }
}