package com.example.domain.usecase

import com.example.domain.model.AddToWishListRequest
import com.example.domain.repositories.wishlist.WishlistRepository
import javax.inject.Inject

class AddProductToWishlistUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository
) {
    suspend fun invoke(addToWishListRequest: AddToWishListRequest, token: String) =
        wishlistRepository.addProductToWishlist(
            addToWishListRequest,
            token
        )
}