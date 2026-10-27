package com.trasladossolidarios.domain.usecase.ride

import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.model.Ride
import com.trasladossolidarios.domain.repository.RidesRepository
import javax.inject.Inject

class GetRideByIdUseCase @Inject constructor(
    private val rideRepository: RidesRepository,
) {
    suspend operator fun invoke(rideId: String): AppResult<Ride> =
        rideRepository.getRideById(rideId)
}
