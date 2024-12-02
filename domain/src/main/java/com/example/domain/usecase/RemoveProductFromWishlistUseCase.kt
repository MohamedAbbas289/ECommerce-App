package com.example.domain.usecase

import com.example.domain.repositories.wishlist.WishlistRepository
import javax.inject.Inject

class RemoveProductFromWishlistUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository
) {
    suspend operator fun invoke(productId: String, token: String) =
        wishlistRepository.removeProductFromWishlist(productId, token)
}