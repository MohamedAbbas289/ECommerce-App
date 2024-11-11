package com.example.domain.usecase

import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.SubCategory
import com.example.domain.repositories.products.ProductsRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repo: ProductsRepository
) {
    suspend fun invokeProductsByCategory(category: Category): List<Product?>? {
        return repo.getProductsByCategory(category)
    }

    suspend fun invokeProductsBySubCategory(subCategory: SubCategory): List<Product?>? {
        return repo.getProductsBySubCategory(subCategory)
    }

}