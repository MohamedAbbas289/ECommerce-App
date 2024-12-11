package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserAddressRequest(
    val phone: String? = null,
    val city: String? = null,
    val name: String? = null,
    val details: String? = null
) : Parcelable