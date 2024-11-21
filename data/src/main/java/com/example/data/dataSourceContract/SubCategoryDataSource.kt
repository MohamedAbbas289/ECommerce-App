package com.example.data.dataSourceContract

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.model.SubCategory
import kotlinx.coroutines.flow.Flow

interface SubCategoryDataSource {
    suspend fun getSubCategories(category: Category): Flow<ResultWrapper<List<SubCategory?>?>>

}