package com.example.domain.model.cart.addToCart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductsItemForAdd(
    val product: String? = null,
    val price: Int? = null,
    val count: Int? = null,
    val id: String? = null
) : Parcelable