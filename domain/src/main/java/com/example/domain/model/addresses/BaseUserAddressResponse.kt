package com.example.domain.model.addresses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BaseUserAddressResponse(
    val addresses: List<Address?>? = null,
    val message: String? = null,
    val status: String? = null
) : Parcelable