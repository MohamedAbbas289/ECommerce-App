package com.example.domain.model.order

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class CartItemsItem(
    val product: String? = null,
    val price: Int? = null,
    val count: Int? = null,
    val id: String? = null
) : Parcelable