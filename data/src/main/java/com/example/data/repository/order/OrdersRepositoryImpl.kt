package com.example.data.repository.order

import com.example.data.dataSourceContract.OrderDataSource
import com.example.domain.common.ResultWrapper
import com.example.domain.model.OrderRequest
import com.example.domain.model.order.CardOrderResponse
import com.example.domain.model.order.CashOrderResponse
import com.example.domain.repositories.order.OrdersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val orderDataSource: OrderDataSource
) : OrdersRepository {
    override suspend fun createCashOrder(
        orderRequest: OrderRequest,
        cartId: String,
        token: String
    ): Flow<ResultWrapper<CashOrderResponse>> {
        return orderDataSource.createCashOrder(orderRequest, cartId, token)
    }

    override suspend fun createOnlineOrder(
        orderRequest: OrderRequest,
        cartId: String,
        token: String
    ): Flow<ResultWrapper<CardOrderResponse>> {
        return orderDataSource.createOnlineOrder(orderRequest, cartId, token)
    }
}