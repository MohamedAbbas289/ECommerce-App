package com.example.data.model.order

import com.example.domain.model.order.CardOrderResponse
import com.google.gson.annotations.SerializedName

data class CardOrderResponseDto(

    @field:SerializedName("session")
    val sessionDto: SessionDto? = null,

    @field:SerializedName("status")
    val status: String? = null
) {
    fun toCardOrderResponse() = CardOrderResponse(
        session = sessionDto?.toSession(),
        status = status
    )
}