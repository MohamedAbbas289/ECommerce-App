package com.example.domain.repositories.subCategories

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.model.SubCategory
import kotlinx.coroutines.flow.Flow

interface SubCategoriesRepository {
    suspend fun getSubCategories(category: Category): Flow<ResultWrapper<List<SubCategory?>?>>

}