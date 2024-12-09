package com.example.domain.usecase

import com.example.domain.model.AddToCartRequest
import com.example.domain.repositories.cart.CartRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(
        addToCartRequest: AddToCartRequest,
        token: String
    ) = cartRepository.addToCart(addToCartRequest, token)


}