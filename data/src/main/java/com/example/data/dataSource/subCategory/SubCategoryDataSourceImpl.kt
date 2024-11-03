package com.example.data.dataSource.subCategory

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.SubCategoryDataSource
import com.example.domain.model.Category
import com.example.domain.model.SubCategory
import javax.inject.Inject

class SubCategoryDataSourceImpl @Inject constructor(
    private val webServices: WebServices
) : SubCategoryDataSource {
    override suspend fun getSubCategories(category: Category): List<SubCategory?>? {
        val response = webServices.getSubCategories(category.id!!)
        return response.data?.map {
            it?.toSubCategory()
        }
    }

}