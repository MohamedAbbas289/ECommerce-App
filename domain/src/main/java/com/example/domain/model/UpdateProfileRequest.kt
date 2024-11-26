package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpdateProfileRequest(
    val phone: String? = null,
    val name: String? = null,
    val email: String? = null
) : Parcelable

