package com.example.domain.repositories.addresses

import com.example.domain.common.ResultWrapper
import com.example.domain.model.UserAddressRequest
import com.example.domain.model.addresses.BaseUserAddressResponse
import kotlinx.coroutines.flow.Flow

interface UserAddressesRepository {
    suspend fun addAddress(
        address: UserAddressRequest,
        token: String
    ): Flow<ResultWrapper<BaseUserAddressResponse>>

    suspend fun removeAddress(
        addressId: String,
        token: String
    ): Flow<ResultWrapper<BaseUserAddressResponse>>

    suspend fun getUserAddresses(
        token: String
    ): Flow<ResultWrapper<BaseUserAddressResponse>>
}