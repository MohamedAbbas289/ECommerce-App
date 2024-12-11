package com.example.data.model.addresses

import com.example.domain.model.addresses.BaseUserAddressResponse
import com.google.gson.annotations.SerializedName

data class BaseUserAddressResponseDto(

    @field:SerializedName("data")
    val addresses: List<AddressDto?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
) {
    fun toBaseUserAddressResponse() = BaseUserAddressResponse(
        addresses = addresses?.map { it?.toAddress() },
        message = message,
        status = status
    )
}