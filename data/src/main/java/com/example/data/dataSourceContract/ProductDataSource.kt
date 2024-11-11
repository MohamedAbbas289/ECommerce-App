package com.example.data.dataSourceContract

import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.SubCategory

interface ProductDataSource {
    suspend fun getProductsByCategory(category: Category): List<Product?>?
    suspend fun getProductsBySubCategory(subCategory: SubCategory): List<Product?>?

}