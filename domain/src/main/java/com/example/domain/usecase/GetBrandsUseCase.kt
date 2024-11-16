package com.example.domain.usecase

import com.example.domain.model.Brand
import com.example.domain.repositories.brands.BrandsRepository
import javax.inject.Inject

class GetBrandsUseCase @Inject constructor(
    private val brandsRepository: BrandsRepository
) {
    suspend fun invoke(): List<Brand?>? {
        return brandsRepository.getBrands()
    }

}