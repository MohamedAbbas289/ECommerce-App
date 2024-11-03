package com.example.data.dataSource

import com.example.data.dataSource.category.CategoryDataSourceImpl
import com.example.data.dataSource.subCategory.SubCategoryDataSourceImpl
import com.example.data.dataSourceContract.CategoryDataSource
import com.example.data.dataSourceContract.SubCategoryDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class Di {
    @Binds
    abstract fun bindCategoryDataSource(
        categoryDataSourceImpl: CategoryDataSourceImpl
    ): CategoryDataSource

    @Binds
    abstract fun bindSubCategoryDataSource(
        subCategoryDataSourceImpl: SubCategoryDataSourceImpl
    ): SubCategoryDataSource
}
