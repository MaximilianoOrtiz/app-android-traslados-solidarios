package com.trasladossolidarios.domain.usecase.location

import com.trasladossolidarios.domain.model.Location
import com.trasladossolidarios.domain.repository.LocationRepository

class SearchAddressesUseCase constructor(
    private val locationRepository: LocationRepository
) {

    suspend operator fun invoke(
        query: String
    ): List<Location> {
        return locationRepository.searchAddresses(query)
    }
}