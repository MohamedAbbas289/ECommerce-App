package com.example.data.repository.SubCategory

import com.example.data.dataSourceContract.SubCategoryDataSource
import com.example.domain.model.Category
import com.example.domain.model.SubCategory
import com.example.domain.repositories.subCategories.SubCategoriesRepository
import javax.inject.Inject

class SubCategoryRepositoryImpl @Inject constructor(
    private val subCategoryDataSource: SubCategoryDataSource
) : SubCategoriesRepository {
    override suspend fun getSubCategories(category: Category): List<SubCategory?>? {
        return subCategoryDataSource.getSubCategories(category)
    }
}