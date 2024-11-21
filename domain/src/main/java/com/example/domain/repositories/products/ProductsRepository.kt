package com.example.domain.repositories.products

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Brand
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.SubCategory
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getProductsByCategory(category: Category): Flow<ResultWrapper<List<Product?>?>>
    suspend fun getProductsBySubCategory(subCategory: SubCategory): Flow<ResultWrapper<List<Product?>?>>
    suspend fun getProductsByBrand(brand: Brand): Flow<ResultWrapper<List<Product?>?>>
}