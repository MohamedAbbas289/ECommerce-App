package com.example.data.dataSource.wishlist

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.WishlistDataSource
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.AddToWishListRequest
import com.example.domain.model.Product
import com.example.domain.model.wishlist.BaseWishlistResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WishlistDataSourceImpl @Inject constructor(
    private val webServices: WebServices
) : WishlistDataSource {
    override suspend fun addProductToWishlist(
        addToWishListRequest: AddToWishListRequest,
        token: String
    ): Flow<ResultWrapper<BaseWishlistResponse>> {
        return safeApiCall {
            webServices.addProductToWishlist(addToWishListRequest, token)
                .toWishlistResponse()
        }
    }

    override suspend fun getWishlist(token: String): Flow<ResultWrapper<List<Product?>?>> {
        return safeApiCall {
            webServices.getWishlist(token).data?.map {
                it?.toProduct()
            }
        }
    }

    override suspend fun removeProductFromWishlist(
        productId: String,
        token: String
    ): Flow<ResultWrapper<BaseWishlistResponse>> {
        return safeApiCall {
            webServices.removeProductFromWishlist(productId, token)
                .toWishlistResponse()
        }
    }
}