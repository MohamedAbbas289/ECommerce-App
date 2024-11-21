package com.example.domain.usecase

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Brand
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.SubCategory
import com.example.domain.repositories.products.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repo: ProductsRepository
) {
    suspend fun invokeProductsByCategory(category: Category): Flow<ResultWrapper<List<Product?>?>> {
        return repo.getProductsByCategory(category)
    }

    suspend fun invokeProductsBySubCategory(subCategory: SubCategory): Flow<ResultWrapper<List<Product?>?>> {
        return repo.getProductsBySubCategory(subCategory)
    }

    suspend fun invokeProductsByBrand(brand: Brand): Flow<ResultWrapper<List<Product?>?>> {
        return repo.getProductsByBrand(brand)
    }


}