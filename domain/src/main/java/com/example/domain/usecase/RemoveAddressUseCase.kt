package com.example.domain.usecase

import com.example.domain.repositories.addresses.UserAddressesRepository
import javax.inject.Inject

class RemoveAddressUseCase @Inject constructor(
    private val userAddressesRepository: UserAddressesRepository
) {
    suspend operator fun invoke(productId: String, token: String) =
        userAddressesRepository.removeAddress(productId, token)
}