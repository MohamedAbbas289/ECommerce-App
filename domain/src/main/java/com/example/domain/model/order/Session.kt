package com.example.domain.model.order

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Session(
    val cancelUrl: String? = null,
    val url: String? = null,
    val successUrl: String? = null
) : Parcelable