package com.example.data.repository.category

import com.example.data.dataSourceContract.CategoryDataSource
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.repositories.categories.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDataSource: CategoryDataSource
) : CategoriesRepository {
    override suspend fun getCategories(page: Int): Flow<ResultWrapper<List<Category?>?>> {
        val categoriesList = categoryDataSource.getCategories()
        return categoriesList
    }
}