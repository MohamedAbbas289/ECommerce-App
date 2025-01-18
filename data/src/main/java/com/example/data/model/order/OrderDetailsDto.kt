package com.example.data.model.order

import com.example.domain.model.order.OrderDetails
import com.google.gson.annotations.SerializedName

data class OrderDetailsDto(

    @field:SerializedName("totalOrderPrice")
    val totalOrderPrice: Int? = null,

    @field:SerializedName("isPaid")
    val isPaid: Boolean? = null,

    @field:SerializedName("isDelivered")
    val isDelivered: Boolean? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("shippingPrice")
    val shippingPrice: Int? = null,

    @field:SerializedName("__v")
    val v: Int? = null,

    @field:SerializedName("taxPrice")
    val taxPrice: Int? = null,

    @field:SerializedName("shippingAddress")
    val shippingAddressDto: ShippingAddressDto? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("cartItems")
    val cartItems: List<CartItemsItemDto?>? = null,

    @field:SerializedName("paymentMethodType")
    val paymentMethodType: String? = null,

    @field:SerializedName("user")
    val user: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
) {
    fun toOrderDetails() = OrderDetails(
        totalOrderPrice = totalOrderPrice,
        isPaid = isPaid,
        isDelivered = isDelivered,
        createdAt = createdAt,
        shippingPrice = shippingPrice,
        taxPrice = taxPrice,
        shippingAddress = shippingAddressDto?.toShippingAddress(),
        id = id,
        cartItems = cartItems?.map { it?.toCartItemsItem() },
        paymentMethodType = paymentMethodType,
    )
}