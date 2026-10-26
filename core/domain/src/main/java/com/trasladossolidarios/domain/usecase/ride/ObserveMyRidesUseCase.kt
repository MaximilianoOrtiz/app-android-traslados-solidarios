package com.trasladossolidarios.domain.usecase.ride

import com.trasladossolidarios.domain.model.Ride
import com.trasladossolidarios.domain.enum.RideStatus
import com.trasladossolidarios.domain.repository.RidesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveMyRidesUseCase @Inject constructor(
    private val repository: RidesRepository,
) {
    operator fun invoke(userId: String): Flow<List<Ride>> =
        repository.observeMyRides(userId).map { rides ->
            rides.sortedWith(
                compareBy<Ride> {
                    it.status == RideStatus.COMPLETED || it.status == RideStatus.CANCELLED
                }.thenBy { it.dateOfTransfer }
            )
        }
}