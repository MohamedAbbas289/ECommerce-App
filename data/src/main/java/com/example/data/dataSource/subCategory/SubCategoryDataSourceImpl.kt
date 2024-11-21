package com.example.data.dataSource.subCategory

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.SubCategoryDataSource
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.model.SubCategory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubCategoryDataSourceImpl @Inject constructor(
    private val webServices: WebServices
) : SubCategoryDataSource {
    override suspend fun getSubCategories(category: Category)
            : Flow<ResultWrapper<List<SubCategory?>?>> {
        val response = webServices.getSubCategories(category.id!!)
        return safeApiCall {
            response.data?.map {
                it?.toSubCategory()
            }
        }
    }

}