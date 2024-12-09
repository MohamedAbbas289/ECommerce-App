package com.example.domain.usecase

import com.example.domain.model.UpdateQuantityRequest
import com.example.domain.repositories.cart.CartRepository
import javax.inject.Inject

class UpdateProductQuantityUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(
        productId: String,
        updateQuantityRequest: UpdateQuantityRequest,
        token: String
    ) = cartRepository.updateCartProductQuantity(productId, updateQuantityRequest, token)
}