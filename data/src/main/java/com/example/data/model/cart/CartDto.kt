package com.example.data.model.cart

import com.example.domain.model.cart.Cart
import com.example.domain.model.cart.ProductsItem
import com.google.gson.annotations.SerializedName

data class CartDto(

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
    val products: List<ProductsItem?>? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
) {
    fun toCart() = Cart(
        cartOwner = cartOwner,
        createdAt = createdAt,
        totalCartPrice = totalCartPrice,
        v = v,
        id = id,
        products = products
    )
}