package com.trasladossolidarios.domain.usecase.location

import com.trasladossolidarios.domain.model.Location
import com.trasladossolidarios.domain.repository.LocationRepository

class GetCurrentLocationUseCase constructor(
    private val locationRepository: LocationRepository
) {

    suspend operator fun invoke(): Location? {
        return locationRepository.getCurrentLocation()
    }
}