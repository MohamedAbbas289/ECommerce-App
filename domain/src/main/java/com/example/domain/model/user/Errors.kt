package com.example.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Errors(
    val msg: String? = null,
    val param: String? = null,
    val location: String? = null,
    val value: String? = null
) : Parcelable
