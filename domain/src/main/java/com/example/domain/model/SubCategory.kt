package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubCategory(
    val createdAt: String? = null,
    val name: String? = null,
    val id: String? = null,
    val category: String? = null,
    val slug: String? = null,
    val updatedAt: String? = null
) : Parcelable
