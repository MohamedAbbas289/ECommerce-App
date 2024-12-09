package com.example.domain.model.cart.addToCart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartForAdd(
    val cartOwner: String? = null,
    val createdAt: String? = null,
    val totalCartPrice: Int? = null,
    val v: Int? = null,
    val id: String? = null,
    val products: List<ProductsItemForAdd?>? = null,
    val updatedAt: String? = null
) : Parcelable