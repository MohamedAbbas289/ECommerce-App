package com.example.domain.usecase

import com.example.domain.repositories.cart.CartRepository
import javax.inject.Inject

class ClearCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(token: String) =
        cartRepository.clearCart(token)
}