package com.example.domain.repositories.brands

import com.example.domain.model.Brand

interface BrandsRepository {
    suspend fun getBrands(): List<Brand?>?
}