package com.example.data.model.order

import com.example.domain.model.order.Session
import com.google.gson.annotations.SerializedName

data class SessionDto(

    @field:SerializedName("cancel_url")
    val cancelUrl: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("success_url")
    val successUrl: String? = null
) {
    fun toSession() = Session(
        cancelUrl = cancelUrl,
        url = url,
        successUrl = successUrl
    )
}