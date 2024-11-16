package com.example.data.model.brand

import com.google.gson.annotations.SerializedName

data class BrandResponse(

    @field:SerializedName("metadata")
    val metadata: Metadata? = null,

    @field:SerializedName("data")
    val data: List<BrandDto?>? = null,

    @field:SerializedName("results")
    val results: Int? = null
)