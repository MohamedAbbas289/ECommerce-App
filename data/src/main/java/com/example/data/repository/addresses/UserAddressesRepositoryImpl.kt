package com.example.data.repository.addresses

import com.example.data.dataSourceContract.UserAddressesDataSource
import com.example.domain.common.ResultWrapper
import com.example.domain.model.UserAddressRequest
import com.example.domain.model.addresses.BaseUserAddressResponse
import com.example.domain.repositories.addresses.UserAddressesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserAddressesRepositoryImpl @Inject constructor(
    private val userAddressesDataSource: UserAddressesDataSource
) : UserAddressesRepository {
    override suspend fun addAddress(
        address: UserAddressRequest,
        token: String
    ): Flow<ResultWrapper<BaseUserAddressResponse>> {
        return userAddressesDataSource.addAddress(address, token)
    }

    override suspend fun removeAddress(
        addressId: String,
        token: String
    ): Flow<ResultWrapper<BaseUserAddressResponse>> {
        return userAddressesDataSource.removeAddress(addressId, token)
    }

    override suspend fun getUserAddresses(token: String): Flow<ResultWrapper<BaseUserAddressResponse>> {
        return userAddressesDataSource.getUserAddresses(token)
    }
}