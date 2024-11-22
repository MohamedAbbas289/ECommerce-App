package com.example.domain.usecase

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.model.SubCategory
import com.example.domain.repositories.subCategories.SubCategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSubCategoriesUseCase @Inject constructor(
    private val repo: SubCategoriesRepository
) {
    suspend fun invoke(category: Category): Flow<ResultWrapper<List<SubCategory?>?>> {
        return repo.getSubCategories(category)
    }
}