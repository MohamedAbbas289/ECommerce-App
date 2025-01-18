package com.example.data.repository

import com.example.data.repository.SubCategory.SubCategoryRepositoryImpl
import com.example.data.repository.addresses.UserAddressesRepositoryImpl
import com.example.data.repository.brand.BrandRepositoryImpl
import com.example.data.repository.cart.CartRepositoryImpl
import com.example.data.repository.category.CategoryRepositoryImpl
import com.example.data.repository.login.LoginRepositoryImpl
import com.example.data.repository.order.OrdersRepositoryImpl
import com.example.data.repository.product.ProductRepositoryImpl
import com.example.data.repository.signup.SignupRepositoryImpl
import com.example.data.repository.updateProfile.UpdateProfileRepositoryImpl
import com.example.data.repository.wishlist.WishlistRepositoryImpl
import com.example.domain.repositories.addresses.UserAddressesRepository
import com.example.domain.repositories.brands.BrandsRepository
import com.example.domain.repositories.cart.CartRepository
import com.example.domain.repositories.categories.CategoriesRepository
import com.example.domain.repositories.login.LoginRepository
import com.example.domain.repositories.order.OrdersRepository
import com.example.domain.repositories.products.ProductsRepository
import com.example.domain.repositories.signup.SignupRepository
import com.example.domain.repositories.subCategories.SubCategoriesRepository
import com.example.domain.repositories.updateProfile.UpdateProfileRepository
import com.example.domain.repositories.wishlist.WishlistRepository
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

    @Binds
    abstract fun bindUpdateProfileRepository(
        updateProfileRepositoryImpl: UpdateProfileRepositoryImpl
    ): UpdateProfileRepository

    @Binds
    abstract fun bindWishlistRepository(
        wishlistRepositoryImpl: WishlistRepositoryImpl
    ): WishlistRepository

    @Binds
    abstract fun bindCartRepository(
        cartRepositoryImpl: CartRepositoryImpl
    ): CartRepository

    @Binds
    abstract fun bindUserAddressesRepository(
        userAddressesRepositoryImpl: UserAddressesRepositoryImpl
    ): UserAddressesRepository

    @Binds
    abstract fun bindOrdersRepository(
        ordersRepositoryImpl: OrdersRepositoryImpl
    ): OrdersRepository
}