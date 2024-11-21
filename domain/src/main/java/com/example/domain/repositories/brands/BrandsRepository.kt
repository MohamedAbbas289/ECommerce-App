package com.example.domain.repositories.brands

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Brand
import kotlinx.coroutines.flow.Flow

interface BrandsRepository {
    suspend fun getBrands(): Flow<ResultWrapper<List<Brand?>?>>
}