package com.example.data.repository

import com.example.data.repository.SubCategory.SubCategoryRepositoryImpl
import com.example.data.repository.category.CategoryRepositoryImpl
import com.example.data.repository.product.ProductRepositoryImpl
import com.example.domain.repositories.categories.CategoriesRepository
import com.example.domain.repositories.products.ProductsRepository
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

    @Binds
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductsRepository

}