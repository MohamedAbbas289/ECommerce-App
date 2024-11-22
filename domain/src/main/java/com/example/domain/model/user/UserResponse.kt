package com.example.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    val statusMsg: String? = null,
    val message: String? = null,
    val user: User? = null,
    val token: String? = null
) : Parcelable