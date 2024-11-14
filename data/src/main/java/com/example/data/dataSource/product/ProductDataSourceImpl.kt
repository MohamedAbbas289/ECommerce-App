package com.example.data.dataSource.product

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.ProductDataSource
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.SubCategory
import javax.inject.Inject

class ProductDataSourceImpl @Inject constructor(
    private val webServices: WebServices
) : ProductDataSource {
    override suspend fun getProductsByCategory(category: Category): List<Product?>? {
        val response = webServices.getProductsByCategory(categoryId = category.id!!)
        return response.data?.map {
            it?.toProduct()
        }
    }

    override suspend fun getProductsBySubCategory(subCategory: SubCategory): List<Product?>? {
        val response = webServices.getProductsBySubCategory(subCategoryId = subCategory.id!!)
        return response.data?.map {
            it?.toProduct()
        }
    }
}