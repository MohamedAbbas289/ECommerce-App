package com.example.data.model.order

import com.example.domain.model.order.ShippingAddress
import com.google.gson.annotations.SerializedName

data class ShippingAddressDto(

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("details")
    val details: String? = null
) {
    fun toShippingAddress() = ShippingAddress(
        phone = phone,
        city = city,
        details = details
    )
}