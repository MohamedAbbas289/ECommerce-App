package com.example.domain.model.user

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class User(
    val role: String? = null,
    val name: String? = null,
    val email: String? = null
) : Parcelable