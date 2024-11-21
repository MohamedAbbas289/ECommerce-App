package com.example.data.dataSource

import com.example.data.dataSource.brand.BrandDataSourceImpl
import com.example.data.dataSource.category.CategoryDataSourceImpl
import com.example.data.dataSource.login.LoginDataSourceImpl
import com.example.data.dataSource.product.ProductDataSourceImpl
import com.example.data.dataSource.signup.SignupDataSourceImpl
import com.example.data.dataSource.subCategory.SubCategoryDataSourceImpl
import com.example.data.dataSourceContract.BrandDataSource
import com.example.data.dataSourceContract.CategoryDataSource
import com.example.data.dataSourceContract.LoginDataSource
import com.example.data.dataSourceContract.ProductDataSource
import com.example.data.dataSourceContract.SignupDataSource
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

    @Binds
    abstract fun bindProductDataSource(
        productDataSourceImpl: ProductDataSourceImpl
    ): ProductDataSource

    @Binds
    abstract fun bindBrandDataSource(
        brandDataSourceImpl: BrandDataSourceImpl
    ): BrandDataSource

    @Binds
    abstract fun bindSignupDataSource(
        signupDataSourceImpl: SignupDataSourceImpl
    ): SignupDataSource

    @Binds
    abstract fun bindLoginDataSource(
        loginDataSourceImpl: LoginDataSourceImpl
    ): LoginDataSource
}
