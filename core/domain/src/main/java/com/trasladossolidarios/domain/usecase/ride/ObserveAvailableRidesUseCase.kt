package com.trasladossolidarios.domain.usecase.ride

import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.model.Ride
import com.trasladossolidarios.domain.repository.RidesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAvailableRidesUseCase @Inject constructor(
    private val rideRepository: RidesRepository,
) {
    suspend operator fun invoke(
        currentUserId: String,
    ): Flow<AppResult<List<Ride>>> {
        return rideRepository.observeAvailableRides(currentUserId)
    }
}