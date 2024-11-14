package com.example.data.model.subCategory

import com.google.gson.annotations.SerializedName

data class SubCategoryResponse(

    @field:SerializedName("metadata")
    val metadata: Metadata? = null,

    @field:SerializedName("data")
    val data: List<SubCategoryDto?>? = null,

    @field:SerializedName("results")
    val results: Int? = null
)