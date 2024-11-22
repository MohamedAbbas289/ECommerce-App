package com.example.data.dataSource.product

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.ProductDataSource
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Brand
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.SubCategory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductDataSourceImpl @Inject constructor(
    private val webServices: WebServices
) : ProductDataSource {
    override suspend fun getProductsByCategory(category: Category): Flow<ResultWrapper<List<Product?>?>> {
        val response = safeApiCall {
            webServices.getProductsByCategory(category.id!!).data?.map {
                it?.toProduct()
            }
        }
        return response
    }


    override suspend fun getProductsBySubCategory(subCategory: SubCategory): Flow<ResultWrapper<List<Product?>?>> {
        val response = safeApiCall {
            webServices.getProductsBySubCategory(subCategory.id!!).data?.map {
                it?.toProduct()
            }
        }
        return response
    }

    override suspend fun getProductsByBrand(brand: Brand): Flow<ResultWrapper<List<Product?>?>> {
        val response = safeApiCall {
            webServices.getProductsByBrand(brand.id!!).data?.map {
                it?.toProduct()
            }
        }
        return response
    }
}