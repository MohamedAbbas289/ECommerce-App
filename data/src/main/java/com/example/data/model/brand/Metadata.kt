package com.example.data.model.brand

import com.google.gson.annotations.SerializedName

data class Metadata(

    @field:SerializedName("numberOfPages")
    val numberOfPages: Int? = null,

    @field:SerializedName("nextPage")
    val nextPage: Int? = null,

    @field:SerializedName("limit")
    val limit: Int? = null,

    @field:SerializedName("currentPage")
    val currentPage: Int? = null
)