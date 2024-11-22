package com.example.data.dataSource.category

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.CategoryDataSource
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryDataSourceImpl @Inject constructor(
    private val webServices: WebServices
) : CategoryDataSource {
    override suspend fun getCategories(): Flow<ResultWrapper<List<Category?>?>> {
        val response = safeApiCall {
            webServices.getCategories().data?.map {
            it?.toCategory()
            }
        }
        return response
    }
}
