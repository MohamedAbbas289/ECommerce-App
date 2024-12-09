package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpdateQuantityRequest(
    val count: Int? = null
) : Parcelable
