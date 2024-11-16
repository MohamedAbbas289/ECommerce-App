package com.example.data.repository.product

import com.example.data.dataSourceContract.ProductDataSource
import com.example.domain.model.Brand
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.SubCategory
import com.example.domain.repositories.products.ProductsRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDataSource: ProductDataSource
) : ProductsRepository {
    override suspend fun getProductsByCategory(category: Category): List<Product?>? {
        return productDataSource.getProductsByCategory(category)
    }

    override suspend fun getProductsBySubCategory(subCategory: SubCategory): List<Product?>? {
        return productDataSource.getProductsBySubCategory(subCategory)
    }

    override suspend fun getProductsByBrand(brand: Brand): List<Product?>? {
        return productDataSource.getProductsByBrand(brand)
    }
}