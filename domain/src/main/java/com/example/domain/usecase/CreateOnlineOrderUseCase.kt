package com.example.domain.usecase

import com.example.domain.model.OrderRequest
import com.example.domain.repositories.order.OrdersRepository
import javax.inject.Inject

class CreateOnlineOrderUseCase @Inject constructor(
    private val repo: OrdersRepository
) {
    suspend operator fun invoke(orderRequest: OrderRequest, cartId: String, token: String) =
        repo.createOnlineOrder(orderRequest, cartId, token)
}