package com.example.data.model.cart

import com.example.domain.model.cart.BaseCartResponse
import com.google.gson.annotations.SerializedName

data class BaseCartResponseDto(

    @field:SerializedName("data")
    val cartDto: CartDto? = null,

    @field:SerializedName("numOfCartItems")
    val numOfCartItems: Int? = null,

    @field:SerializedName("cartId")
    val cartId: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
) {
    fun toBaseCartResponse() = BaseCartResponse(
        cart = cartDto?.toCart(),
        numOfCartItems = numOfCartItems,
        cartId = cartId,
        message = message,
        status = status
    )
}