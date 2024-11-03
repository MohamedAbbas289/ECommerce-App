package com.example.domain.usecase

import com.example.domain.model.Category
import com.example.domain.model.SubCategory
import com.example.domain.repositories.subCategories.SubCategoriesRepository
import javax.inject.Inject

class GetSubCategoriesUseCase @Inject constructor(
    private val repo: SubCategoriesRepository
) {
    suspend fun invoke(category: Category): List<SubCategory?>? {
        return repo.getSubCategories(category)
    }
}