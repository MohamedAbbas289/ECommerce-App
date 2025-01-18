package com.example.domain.model.order

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class OrderDetails(
    val totalOrderPrice: Int? = null,
    val isPaid: Boolean? = null,
    val isDelivered: Boolean? = null,
    val createdAt: String? = null,
    val shippingPrice: Int? = null,
    val v: Int? = null,
    val taxPrice: Int? = null,
    val shippingAddress: ShippingAddress? = null,
    val id: String? = null,
    val cartItems: List<CartItemsItem?>? = null,
    val paymentMethodType: String? = null,
    val user: String? = null,
    val updatedAt: String? = null
) : Parcelable