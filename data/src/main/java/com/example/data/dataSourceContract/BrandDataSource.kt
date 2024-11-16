package com.example.data.dataSourceContract

import com.example.domain.model.Brand

interface BrandDataSource {
    suspend fun getBrands(): List<Brand?>?
}