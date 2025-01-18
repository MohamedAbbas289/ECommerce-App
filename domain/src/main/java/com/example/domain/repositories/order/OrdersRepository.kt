package com.example.domain.repositories.order

import com.example.domain.common.ResultWrapper
import com.example.domain.model.OrderRequest
import com.example.domain.model.order.CardOrderResponse
import com.example.domain.model.order.CashOrderResponse
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {
    suspend fun createCashOrder(
        orderRequest: OrderRequest,
        cartId: String,
        token: String
    ): Flow<ResultWrapper<CashOrderResponse>>

    suspend fun createOnlineOrder(
        orderRequest: OrderRequest,
        cartId: String,
        token: String
    ): Flow<ResultWrapper<CardOrderResponse>>


}