package com.example.data.dataSourceContract

import com.example.domain.model.Category
import com.example.domain.model.SubCategory

interface SubCategoryDataSource {
    suspend fun getSubCategories(category: Category): List<SubCategory?>?

}