package com.example.data.repository.cart

import com.example.data.dataSourceContract.CartDataSource
import com.example.domain.common.ResultWrapper
import com.example.domain.model.AddToCartRequest
import com.example.domain.model.UpdateQuantityRequest
import com.example.domain.model.cart.BaseCartResponse
import com.example.domain.model.cart.addToCart.AddToCartResponse
import com.example.domain.repositories.cart.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDataSource: CartDataSource
) : CartRepository {
    override suspend fun addToCart(
        addToCartRequest: AddToCartRequest,
        token: String
    ): Flow<ResultWrapper<AddToCartResponse>> =
        cartDataSource.addToCart(addToCartRequest, token)

    override suspend fun updateCartProductQuantity(
        productId: String,
        updateQuantityRequest: UpdateQuantityRequest,
        token: String
    ): Flow<ResultWrapper<BaseCartResponse>> =
        cartDataSource.updateCartProductQuantity(productId, updateQuantityRequest, token)

    override suspend fun getUserCart(token: String): Flow<ResultWrapper<BaseCartResponse>> {
        return cartDataSource.getUserCart(token)
    }

    override suspend fun removeProductFromCart(
        productId: String,
        token: String
    ): Flow<ResultWrapper<BaseCartResponse>> {
        return cartDataSource.removeProductFromCart(productId, token)
    }

    override suspend fun clearCart(token: String): Flow<ResultWrapper<BaseCartResponse>> {
        return cartDataSource.clearCart(token)
    }

}