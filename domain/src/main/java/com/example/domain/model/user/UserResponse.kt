package com.example.domain.model.user

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class UserResponse(
    val message: String? = null,
    val user: User? = null,
    val token: String? = null
) : Parcelable