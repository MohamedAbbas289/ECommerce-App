package com.example.data.model.cart.addToCart

import com.example.domain.model.cart.addToCart.AddToCartResponse
import com.google.gson.annotations.SerializedName

data class AddToCartResponseDto(

    @field:SerializedName("data")
    val cartDtoForAdd: CartDtoForAdd? = null,

    @field:SerializedName("numOfCartItems")
    val numOfCartItems: Int? = null,

    @field:SerializedName("cartId")
    val cartId: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
) {
    fun toAddToCartResponse() = AddToCartResponse(
        cartForAdd = cartDtoForAdd?.toCartForAdd(),
        numOfCartItems = numOfCartItems,
        cartId = cartId,
        message = message,
        status = status
    )
}