package com.example.domain.usecase

import com.example.domain.model.UserAddressRequest
import com.example.domain.repositories.addresses.UserAddressesRepository
import javax.inject.Inject

class AddAddressUseCase @Inject constructor(
    private val userAddressesRepository: UserAddressesRepository
) {
    suspend operator fun invoke(
        address: UserAddressRequest,
        token: String
    ) = userAddressesRepository.addAddress(address, token)

}