package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddToCartRequest(
    val productId: String? = null
) : Parcelable
