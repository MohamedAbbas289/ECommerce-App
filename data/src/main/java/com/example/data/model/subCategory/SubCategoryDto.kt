package com.example.data.model.subCategory

import com.example.domain.model.SubCategory
import com.google.gson.annotations.SerializedName

data class SubCategoryDto(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("category")
    val category: String? = null,

    @field:SerializedName("slug")
    val slug: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
) {
    fun toSubCategory(): SubCategory {
        return SubCategory(
            createdAt = createdAt,
            name = name,
            id = id,
        )
    }
}