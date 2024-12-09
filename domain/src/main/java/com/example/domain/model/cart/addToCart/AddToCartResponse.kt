package com.example.domain.model.cart.addToCart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddToCartResponse(
    val cartForAdd: CartForAdd? = null,
    val numOfCartItems: Int? = null,
    val cartId: String? = null,
    val message: String? = null,
    val status: String? = null
) : Parcelable