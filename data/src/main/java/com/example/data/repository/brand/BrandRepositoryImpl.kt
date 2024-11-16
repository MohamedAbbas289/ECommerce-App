package com.example.data.repository.brand

import com.example.data.dataSourceContract.BrandDataSource
import com.example.domain.model.Brand
import com.example.domain.repositories.brands.BrandsRepository
import javax.inject.Inject

class BrandRepositoryImpl @Inject constructor(
    private val brandDataSource: BrandDataSource
) : BrandsRepository {
    override suspend fun getBrands(): List<Brand?>? {
        val brandsList = brandDataSource.getBrands()
        return brandsList
    }

}