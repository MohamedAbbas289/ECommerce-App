package com.example.data.repository

import com.example.data.repository.SubCategory.SubCategoryRepositoryImpl
import com.example.data.repository.category.CategoryRepositoryImpl
import com.example.domain.repositories.categories.CategoriesRepository
import com.example.domain.repositories.subCategories.SubCategoriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class Di {
    @Binds
    abstract fun bindCategoriesRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoriesRepository

    @Binds
    abstract fun bindSubCategoryRepository(
        subCategoryRepositoryImpl: SubCategoryRepositoryImpl
    ): SubCategoriesRepository



}