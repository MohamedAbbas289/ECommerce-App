package com.example.domain.model.cart

import android.os.Parcelable
import com.example.domain.model.Product
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductsItem(
    val product: Product? = null,
    val price: Int? = null,
    var count: Int? = null,
    val id: String? = null
) : Parcelable
