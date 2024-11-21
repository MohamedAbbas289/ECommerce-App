package com.example.domain.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class SignupRequest(
    val password: String? = null,
    val phone: String? = null,
    val rePassword: String? = null,
    val name: String? = null,
    val email: String? = null
) : Parcelable
