package com.example.domain.usecase

import com.example.domain.model.OrderRequest
import com.example.domain.model.order.CashOrderResponse
import com.example.domain.repositories.order.OrdersRepository
import javax.inject.Inject

class CreateCashOrderUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository
) {
    suspend operator fun invoke(orderRequest: OrderRequest, cartId: String, token: String) =
        ordersRepository.createCashOrder(orderRequest, cartId, token)
}