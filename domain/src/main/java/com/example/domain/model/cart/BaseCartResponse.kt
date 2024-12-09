package com.example.domain.model.cart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BaseCartResponse(
    val cart: Cart? = null,
    val numOfCartItems: Int? = null,
    val cartId: String? = null,
    val message: String? = null,
    val status: String? = null
) : Parcelable