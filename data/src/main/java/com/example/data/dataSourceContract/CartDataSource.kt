package com.example.data.dataSourceContract

import com.example.domain.common.ResultWrapper
import com.example.domain.model.AddToCartRequest
import com.example.domain.model.UpdateQuantityRequest
import com.example.domain.model.cart.BaseCartResponse
import com.example.domain.model.cart.addToCart.AddToCartResponse
import kotlinx.coroutines.flow.Flow

interface CartDataSource {

    suspend fun addToCart(
        addToCartRequest: AddToCartRequest,
        token: String
    ): Flow<ResultWrapper<AddToCartResponse>>

    suspend fun updateCartProductQuantity(
        productId: String,
        updateQuantityRequest: UpdateQuantityRequest,
        token: String
    ): Flow<ResultWrapper<BaseCartResponse>>

    suspend fun getUserCart(
        token: String
    ): Flow<ResultWrapper<BaseCartResponse>>

    suspend fun removeProductFromCart(
        productId: String,
        token: String
    ): Flow<ResultWrapper<BaseCartResponse>>

    suspend fun clearCart(
        token: String
    ): Flow<ResultWrapper<BaseCartResponse>>

}