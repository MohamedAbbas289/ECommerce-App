package com.example.data.dataSource.category

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.CategoryDataSource
import com.example.domain.model.Category
import javax.inject.Inject

class CategoryDataSourceImpl @Inject constructor(
    private val webServices: WebServices
) : CategoryDataSource {
    override suspend fun getCategories(): List<Category?>? {
        val response = webServices.getCategories()
        return response.data?.map {
            it?.toCategory()
        }
    }
}
