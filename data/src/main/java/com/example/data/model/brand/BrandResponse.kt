package com.example.data.model.brand

import com.example.data.model.BaseResponse
import com.google.gson.annotations.SerializedName

data class BrandResponse(

    @field:SerializedName("metadata")
    val metadata: Metadata? = null,

    @field:SerializedName("results")
    val results: Int? = null
) : BaseResponse<List<BrandDto?>?>()