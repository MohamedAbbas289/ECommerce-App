package com.example.data.dataSourceContract

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Brand
import kotlinx.coroutines.flow.Flow

interface BrandDataSource {
    suspend fun getBrands(): Flow<ResultWrapper<List<Brand?>?>>
}