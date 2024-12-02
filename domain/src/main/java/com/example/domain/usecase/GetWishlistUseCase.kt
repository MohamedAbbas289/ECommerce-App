package com.example.domain.usecase

import com.example.domain.repositories.wishlist.WishlistRepository
import javax.inject.Inject

class GetWishlistUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository
) {
    suspend fun invoke(token: String) =
        wishlistRepository.getWishlist(token)
}