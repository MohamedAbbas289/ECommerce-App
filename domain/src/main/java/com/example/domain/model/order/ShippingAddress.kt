package com.example.domain.model.order

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ShippingAddress(
    val phone: String? = null,
    val city: String? = null,
    val details: String? = null
) : Parcelable