package com.example.data.model.wishlist

import com.example.domain.model.wishlist.BaseWishlistResponse
import com.google.gson.annotations.SerializedName

data class BaseWishlistResponseDto(

    @field:SerializedName("data")
    val data: List<String?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("statusMsg")
    val statusMsg: String? = null

) {
    fun toWishlistResponse() = BaseWishlistResponse(
        data = data,
        message = message,
        status = status,
        statusMsg = statusMsg
    )
}