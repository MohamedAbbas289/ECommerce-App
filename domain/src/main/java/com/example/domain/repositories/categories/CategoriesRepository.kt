package com.example.domain.repositories.categories

import com.example.domain.model.Category

interface CategoriesRepository {
    suspend fun getCategories(page: Int = 1): List<Category?>?
}