package com.example.domain.usecase

import com.example.domain.repositories.addresses.UserAddressesRepository
import javax.inject.Inject

class GetAddressesUseCase @Inject constructor(
    private val userAddressesRepository: UserAddressesRepository
) {
    suspend operator fun invoke(token: String) =
        userAddressesRepository.getUserAddresses(token)
}