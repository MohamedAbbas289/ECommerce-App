package com.example.data.repository

import com.example.data.repository.SubCategory.SubCategoryRepositoryImpl
import com.example.data.repository.brand.BrandRepositoryImpl
import com.example.data.repository.category.CategoryRepositoryImpl
import com.example.data.repository.login.LoginRepositoryImpl
import com.example.data.repository.product.ProductRepositoryImpl
import com.example.data.repository.signup.SignupRepositoryImpl
import com.example.domain.repositories.brands.BrandsRepository
import com.example.domain.repositories.categories.CategoriesRepository
import com.example.domain.repositories.login.LoginRepository
import com.example.domain.repositories.products.ProductsRepository
import com.example.domain.repositories.signup.SignupRepository
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

    @Binds
    abstract fun bindBrandRepository(
        brandRepositoryImpl: BrandRepositoryImpl
    ): BrandsRepository

    @Binds
    abstract fun bindSignupRepository(
        signupRepositoryImpl: SignupRepositoryImpl
    ): SignupRepository

    @Binds
    abstract fun bindLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository
}