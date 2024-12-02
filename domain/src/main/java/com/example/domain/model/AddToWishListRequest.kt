package com.example.domain.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class AddToWishListRequest(
    val productId: String? = null
) : Parcelable
