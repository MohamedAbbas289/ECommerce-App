package com.example.data.dataSource.brand

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.BrandDataSource
import com.example.domain.model.Brand
import javax.inject.Inject

class BrandDataSourceImpl @Inject constructor(
    private val webServices: WebServices
) : BrandDataSource {
    override suspend fun getBrands(): List<Brand?>? {
        val response = webServices.getBrands()
        return response.data?.map {
            it?.toBrand()
        }
    }
}