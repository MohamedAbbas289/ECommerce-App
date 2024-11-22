package com.example.domain.usecase

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Brand
import com.example.domain.repositories.brands.BrandsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBrandsUseCase @Inject constructor(
    private val brandsRepository: BrandsRepository
) {
    suspend fun invoke(): Flow<ResultWrapper<List<Brand?>?>> {
        return brandsRepository.getBrands()
    }

}