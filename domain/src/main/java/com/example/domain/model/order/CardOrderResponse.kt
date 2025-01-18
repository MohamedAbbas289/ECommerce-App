package com.example.domain.model.order

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class CardOrderResponse(
    val session: Session? = null,
    val status: String? = null
) : Parcelable