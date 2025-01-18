package com.example.domain.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.example.domain.model.order.ShippingAddress

@Parcelize
data class OrderRequest(
    val shippingAddress: ShippingAddress? = null
) : Parcelable

