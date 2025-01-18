package com.example.data.model.order

import com.example.data.model.BaseResponse
import com.example.domain.model.order.CashOrderResponse
import com.google.gson.annotations.SerializedName

data class CashOrderResponseDto(

    @field:SerializedName("data")
    val orderDetailsDto: OrderDetailsDto? = null,

    @field:SerializedName("status")
    val status: String? = null
) {
    fun toCashOrderResponse() = CashOrderResponse(
        orderDetails = orderDetailsDto?.toOrderDetails(),
        status = status
    )
}