package com.example.domain.usecase

import com.example.domain.repositories.cart.CartRepository
import javax.inject.Inject

class RemoveFromCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: String, token: String) =
        cartRepository.removeProductFromCart(productId, token)
}