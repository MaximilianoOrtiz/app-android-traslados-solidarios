package com.trasladossolidarios.domain.usecase.ride

import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.model.Ride
import com.trasladossolidarios.domain.enum.RideStatus
import com.trasladossolidarios.domain.repository.RidesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveMyRidesUseCase @Inject constructor(
    private val ridesRepository: RidesRepository,
) {
    operator fun invoke(ownerId: String): Flow<AppResult<List<Ride>>> =
        ridesRepository.observeRidesByOwner(ownerId).map { result ->
            when (result) {
                is AppResult.Success -> AppResult.Success(
                    result.data.sortedBy { it.dateOfTransfer }
                )
                is AppResult.Error -> result
            }
        }
}