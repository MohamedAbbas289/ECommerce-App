package com.example.data.dataSource.cart

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.CartDataSource
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.AddToCartRequest
import com.example.domain.model.UpdateQuantityRequest
import com.example.domain.model.cart.BaseCartResponse
import com.example.domain.model.cart.addToCart.AddToCartResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartDataSourceImpl @Inject constructor(
    private val webServices: WebServices
) : CartDataSource {
    override suspend fun addToCart(
        addToCartRequest: AddToCartRequest,
        token: String
    ): Flow<ResultWrapper<AddToCartResponse>> {
        return safeApiCall {
            webServices.addToCart(addToCartRequest, token)
                .toAddToCartResponse()
        }
    }

    override suspend fun updateCartProductQuantity(
        productId: String,
        updateQuantityRequest: UpdateQuantityRequest,
        token: String
    ): Flow<ResultWrapper<BaseCartResponse>> {
        return safeApiCall {
            webServices.updateCartProductQuantity(productId, updateQuantityRequest, token)
                .toBaseCartResponse()
        }
    }

    override suspend fun getUserCart(token: String): Flow<ResultWrapper<BaseCartResponse>> {
        return safeApiCall {
            webServices.getUserCart(token)
                .toBaseCartResponse()
        }
    }

    override suspend fun removeProductFromCart(
        productId: String,
        token: String
    ): Flow<ResultWrapper<BaseCartResponse>> {
        return safeApiCall {
            webServices.removeProductFromCart(productId, token)
                .toBaseCartResponse()
        }
    }

    override suspend fun clearCart(token: String): Flow<ResultWrapper<BaseCartResponse>> {
        return safeApiCall {
            webServices.clearCart(token)
                .toBaseCartResponse()
        }
    }
}