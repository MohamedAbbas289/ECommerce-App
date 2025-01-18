package com.example.data.dataSource.order

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.OrderDataSource
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.OrderRequest
import com.example.domain.model.order.CardOrderResponse
import com.example.domain.model.order.CashOrderResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderDataSourceImpl @Inject constructor(
    private val webServices: WebServices
) : OrderDataSource {
    override suspend fun createCashOrder(
        orderRequest: OrderRequest,
        cartId: String,
        token: String
    ): Flow<ResultWrapper<CashOrderResponse>> {
        return safeApiCall {
            webServices.createCashOrder(orderRequest, cartId, token)
                .toCashOrderResponse()
        }
    }

    override suspend fun createOnlineOrder(
        orderRequest: OrderRequest,
        cartId: String,
        token: String
    ): Flow<ResultWrapper<CardOrderResponse>> {
        return safeApiCall {
            webServices.createOnlineOrder(orderRequest, cartId, token)
                .toCardOrderResponse()
        }
    }
}