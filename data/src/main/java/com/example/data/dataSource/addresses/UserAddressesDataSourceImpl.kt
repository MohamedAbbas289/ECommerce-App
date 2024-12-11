package com.example.data.dataSource.addresses

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.UserAddressesDataSource
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.UserAddressRequest
import com.example.domain.model.addresses.BaseUserAddressResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserAddressesDataSourceImpl @Inject constructor(
    private val webServices: WebServices
) : UserAddressesDataSource {
    override suspend fun addAddress(
        address: UserAddressRequest,
        token: String
    ): Flow<ResultWrapper<BaseUserAddressResponse>> {
        return safeApiCall {
            webServices.addAddress(address, token)
                .toBaseUserAddressResponse()
        }
    }

    override suspend fun removeAddress(
        addressId: String,
        token: String
    ): Flow<ResultWrapper<BaseUserAddressResponse>> {
        return safeApiCall {
            webServices.removeAddress(addressId, token)
                .toBaseUserAddressResponse()
        }
    }

    override suspend fun getUserAddresses(token: String): Flow<ResultWrapper<BaseUserAddressResponse>> {
        return safeApiCall {
            webServices.getUserAddresses(token)
                .toBaseUserAddressResponse()
        }
    }
}