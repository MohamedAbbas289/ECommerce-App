package com.example.data.dataSource.brand

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.BrandDataSource
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Brand
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BrandDataSourceImpl @Inject constructor(
    private val webServices: WebServices
) : BrandDataSource {
    override suspend fun getBrands(): Flow<ResultWrapper<List<Brand?>?>> {
        val response = webServices.getBrands()
        return safeApiCall {
            response.data?.map {
                it?.toBrand()
            }
        }
    }
}
