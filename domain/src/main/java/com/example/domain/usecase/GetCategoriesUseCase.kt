package com.example.domain.usecase

import com.example.domain.model.Category
import com.example.domain.repositories.categories.CategoriesRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repo: CategoriesRepository
) {
    suspend fun invoke(): List<Category?>? {
        return repo.getCategories()
    }

}