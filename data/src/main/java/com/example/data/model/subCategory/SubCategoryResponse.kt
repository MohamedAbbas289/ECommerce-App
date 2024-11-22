package com.example.data.model.subCategory

import com.example.data.model.BaseResponse
import com.google.gson.annotations.SerializedName

data class SubCategoryResponse(

    @field:SerializedName("metadata")
    val metadata: Metadata? = null,

    @field:SerializedName("results")
    val results: Int? = null
) : BaseResponse<List<SubCategoryDto?>?>()