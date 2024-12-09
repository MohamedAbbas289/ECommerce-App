package com.example.data.model.cart.addToCart

import com.example.domain.model.cart.addToCart.CartForAdd
import com.example.domain.model.cart.addToCart.ProductsItemForAdd
import com.google.gson.annotations.SerializedName

data class CartDtoForAdd(

    @field:SerializedName("cartOwner")
    val cartOwner: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("totalCartPrice")
    val totalCartPrice: Int? = null,

    @field:SerializedName("__v")
    val v: Int? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("products")
    val products: List<ProductsItemForAdd?>? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
) {
    fun toCartForAdd() = CartForAdd(
        cartOwner = cartOwner,
        createdAt = createdAt,
        totalCartPrice = totalCartPrice,
        v = v,
        id = id,
        products = products,
        updatedAt = updatedAt
    )
}