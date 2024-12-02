package com.example.data.repository.wishlist

import com.example.data.dataSourceContract.WishlistDataSource
import com.example.domain.common.ResultWrapper
import com.example.domain.model.AddToWishListRequest
import com.example.domain.model.Product
import com.example.domain.model.wishlist.BaseWishlistResponse
import com.example.domain.repositories.wishlist.WishlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    private val wishlistDataSource: WishlistDataSource
) : WishlistRepository {
    override suspend fun addProductToWishlist(
        addToWishlistRequest: AddToWishListRequest,
        token: String
    ): Flow<ResultWrapper<BaseWishlistResponse>> {
        return wishlistDataSource.addProductToWishlist(addToWishlistRequest, token)
    }

    override suspend fun getWishlist(token: String): Flow<ResultWrapper<List<Product?>?>> {
        return wishlistDataSource.getWishlist(token)
    }

    override suspend fun removeProductFromWishlist(
        productId: String,
        token: String
    ): Flow<ResultWrapper<BaseWishlistResponse>> {
        return wishlistDataSource.removeProductFromWishlist(productId, token)
    }
}