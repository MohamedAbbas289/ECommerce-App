package com.example.data.api

import com.example.data.model.brand.BrandResponse
import com.example.data.model.category.CategoriesResponse
import com.example.data.model.product.ProductResponse
import com.example.data.model.subCategory.SubCategoryResponse
import com.example.data.model.user.AuthResponse
import com.example.domain.model.LoginRequest
import com.example.domain.model.SignupRequest
import com.example.domain.model.UpdateProfileRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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

}