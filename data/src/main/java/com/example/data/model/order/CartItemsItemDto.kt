package com.example.data.model.order

import com.example.domain.model.order.CartItemsItem
import com.google.gson.annotations.SerializedName

data class CartItemsItemDto(

    @field:SerializedName("product")
    val product: String? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("count")
    val count: Int? = null,

    @field:SerializedName("_id")
    val id: String? = null
) {
    fun toCartItemsItem() = CartItemsItem(
        product = product,
        price = price,
        count = count,
        id = id
    )
}