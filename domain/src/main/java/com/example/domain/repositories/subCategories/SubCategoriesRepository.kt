package com.example.domain.repositories.subCategories

import com.example.domain.model.Category
import com.example.domain.model.SubCategory

interface SubCategoriesRepository {
    suspend fun getSubCategories(category: Category): List<SubCategory?>?

}