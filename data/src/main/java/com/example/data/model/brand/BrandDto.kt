package com.example.data.model.brand

import com.example.domain.model.Brand
import com.google.gson.annotations.SerializedName

data class BrandDto(

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
    fun toBrand(): Brand {
        return Brand(
            image = image,
            name = name,
            id = id,
            slug = slug,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

}