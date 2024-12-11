package com.example.domain.model.addresses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val name: String? = null,
    val details: String? = null,
    val phone: String? = null,
    val city: String? = null,
    val id: String? = null
) : Parcelable