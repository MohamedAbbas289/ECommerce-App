package com.example.domain.model.cart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cart(
    val cartOwner: String? = null,
    val createdAt: String? = null,
    val totalCartPrice: Int? = null,
    val v: Int? = null,
    val id: String? = null,
    val products: List<ProductsItem?>? = null,
    val updatedAt: String? = null
) : Parcelable