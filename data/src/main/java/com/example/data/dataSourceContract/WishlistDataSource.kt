package com.example.data.dataSourceContract

import com.example.domain.common.ResultWrapper
import com.example.domain.model.AddToWishListRequest
import com.example.domain.model.Product
import com.example.domain.model.wishlist.BaseWishlistResponse
import kotlinx.coroutines.flow.Flow

interface WishlistDataSource {
    suspend fun addProductToWishlist(
        addToWishListRequest: AddToWishListRequest,
        token: String
    ): Flow<ResultWrapper<BaseWishlistResponse>>

    suspend fun getWishlist(token: String): Flow<ResultWrapper<List<Product?>?>>

    suspend fun removeProductFromWishlist(
        productId: String,
        token: String
    ): Flow<ResultWrapper<BaseWishlistResponse>>

}