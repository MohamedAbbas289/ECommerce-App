package com.example.data.api

import com.example.data.model.category.CategoriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("api/v1/categories")
    suspend fun getCategories(@Query("page") page: Int = 1): CategoriesResponse
}