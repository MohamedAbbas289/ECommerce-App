package com.example.data.api

import com.example.data.model.addresses.BaseUserAddressResponseDto
import com.example.data.model.brand.BrandResponse
import com.example.data.model.cart.BaseCartResponseDto
import com.example.data.model.cart.addToCart.AddToCartResponseDto
import com.example.data.model.category.CategoriesResponse
import com.example.data.model.order.CardOrderResponseDto
import com.example.data.model.order.CashOrderResponseDto
import com.example.data.model.product.ProductResponse
import com.example.data.model.subCategory.SubCategoryResponse
import com.example.data.model.user.AuthResponse
import com.example.data.model.wishlist.BaseWishlistResponseDto
import com.example.data.model.wishlist.WishlistResponse
import com.example.domain.model.AddToCartRequest
import com.example.domain.model.AddToWishListRequest
import com.example.domain.model.LoginRequest
import com.example.domain.model.OrderRequest
import com.example.domain.model.SignupRequest
import com.example.domain.model.UpdateProfileRequest
import com.example.domain.model.UpdateQuantityRequest
import com.example.domain.model.UserAddressRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface WebServices {
    @GET("api/v1/categories")
    suspend fun getCategories(@Query("page") page: Int = 1): CategoriesResponse

    @GET("api/v1/subcategories")
    suspend fun getSubCategories(@Query("category") categoryId: String): SubCategoryResponse

    @GET("api/v1/brands")
    suspend fun getBrands(): BrandResponse

    @GET("api/v1/products")
    suspend fun getProductsBySubCategory(@Query("subcategory") subCategoryId: String): ProductResponse

    @GET("api/v1/products")
    suspend fun getProductsByCategory(@Query("category") categoryId: String): ProductResponse

    @GET("api/v1/products")
    suspend fun getProductsByBrand(@Query("brand") brandId: String): ProductResponse

    @POST("api/v1/auth/signup")
    suspend fun signup(@Body signupRequest: SignupRequest): AuthResponse

    @POST("api/v1/auth/signin")
    suspend fun login(@Body loginRequest: LoginRequest): AuthResponse

    @PUT("api/v1/users/updateMe/")
    suspend fun updateProfile(
        @Body updateProfileRequest: UpdateProfileRequest,
        @Query("token") token: String
    ): AuthResponse

    @POST("api/v1/wishlist")
    suspend fun addProductToWishlist(
        @Body addToWishListRequest: AddToWishListRequest,
        @Query("token") token: String
    ): BaseWishlistResponseDto

    @GET("api/v1/wishlist")
    suspend fun getWishlist(@Query("token") token: String): WishlistResponse

    @DELETE("api/v1/wishlist/{id}")
    suspend fun removeProductFromWishlist(
        @Path("id") productId: String,
        @Query("token") token: String
    ): BaseWishlistResponseDto

    @POST("api/v1/cart")
    suspend fun addToCart(
        @Body addToCartRequest: AddToCartRequest,
        @Query("token") token: String
    ): AddToCartResponseDto

    @PUT("api/v1/cart/{id}")
    suspend fun updateCartProductQuantity(
        @Path("id") productId: String,
        @Body updateQuantityRequest: UpdateQuantityRequest,
        @Query("token") token: String
    ): BaseCartResponseDto

    @GET("api/v1/cart")
    suspend fun getUserCart(@Query("token") token: String): BaseCartResponseDto

    @DELETE("api/v1/cart/{id}")
    suspend fun removeProductFromCart(
        @Path("id") productId: String,
        @Query("token") token: String
    ): BaseCartResponseDto

    @DELETE("api/v1/cart")
    suspend fun clearCart(@Query("token") token: String): BaseCartResponseDto

    @POST("api/v1/addresses")
    suspend fun addAddress(
        @Body address: UserAddressRequest,
        @Query("token") token: String
    ): BaseUserAddressResponseDto

    @GET("api/v1/addresses")
    suspend fun getUserAddresses(@Query("token") token: String): BaseUserAddressResponseDto

    @DELETE("api/v1/addresses/{id}")
    suspend fun removeAddress(
        @Path("id") addressId: String,
        @Query("token") token: String
    ): BaseUserAddressResponseDto

    @POST("api/v1/orders/{id}")
    suspend fun createCashOrder(
        @Body orderRequest: OrderRequest,
        @Path("id") cartId: String,
        @Query("token") token: String
    ): CashOrderResponseDto

    @POST("api/v1/orders/checkout-session/{id}")
    suspend fun createOnlineOrder(
        @Body orderRequest: OrderRequest,
        @Path("id") cartId: String,
        @Query("token") token: String
    ): CardOrderResponseDto

}