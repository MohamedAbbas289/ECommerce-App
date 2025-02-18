package com.example.data.api

import com.example.data.model.category.CategoriesResponse
import com.example.data.model.product.ProductResponse
import com.example.data.model.subCategory.SubCategoryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("api/v1/categories")
    suspend fun getCategories(@Query("page") page: Int = 1): CategoriesResponse

    @GET("api/v1/subcategories")
    suspend fun getSubCategories(@Query("category") categoryId: String): SubCategoryResponse

    @GET("api/v1/products")
    suspend fun getProductsBySubCategory(@Query("subcategory") subCategoryId: String): ProductResponse

    @GET("api/v1/products")
    suspend fun getProductsByCategory(@Query("category") categoryId: String): ProductResponse


}