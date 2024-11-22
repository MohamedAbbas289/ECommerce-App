package com.example.domain.usecase

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.repositories.categories.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repo: CategoriesRepository
) {
    suspend fun invoke(): Flow<ResultWrapper<List<Category?>?>> {
        return repo.getCategories()
    }

}