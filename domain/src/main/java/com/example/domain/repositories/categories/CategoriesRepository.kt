package com.example.domain.repositories.categories

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
    suspend fun getCategories(page: Int = 1): Flow<ResultWrapper<List<Category?>?>>
}