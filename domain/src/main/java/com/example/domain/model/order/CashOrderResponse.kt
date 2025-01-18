package com.example.domain.model.order

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class CashOrderResponse(
    val orderDetails: OrderDetails? = null,
    val status: String? = null
) : Parcelable