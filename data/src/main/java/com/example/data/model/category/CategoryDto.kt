package com.example.data.model.category

import com.example.domain.model.Category
import com.google.gson.annotations.SerializedName

data class CategoryDto(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("slug")
    val slug: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
) {
    fun toCategory(): Category {
        return Category(
            image = image,
            name = name,
            id = id,
            slug = slug,
        )
    }
}