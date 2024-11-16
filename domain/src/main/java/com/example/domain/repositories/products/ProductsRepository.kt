package com.example.domain.repositories.products

import com.example.domain.model.Brand
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.SubCategory

interface ProductsRepository {
    suspend fun getProductsByCategory(category: Category): List<Product?>?
    suspend fun getProductsBySubCategory(subCategory: SubCategory): List<Product?>?
    suspend fun getProductsByBrand(brand: Brand): List<Product?>?
}