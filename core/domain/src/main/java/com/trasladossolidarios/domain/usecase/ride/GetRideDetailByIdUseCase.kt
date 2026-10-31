package com.trasladossolidarios.domain.usecase.ride

import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.model.Ride
import com.trasladossolidarios.domain.model.RideDetail
import com.trasladossolidarios.domain.repository.RidesRepository
import com.trasladossolidarios.domain.repository.UserRepository
import javax.inject.Inject

class GetRideDetailByIdUseCase @Inject constructor(
    private val rideRepository: RidesRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(rideId: String): AppResult<RideDetail> {

        return when (val rideResult = rideRepository.getRideById(rideId)) {

            is AppResult.Error -> rideResult

            is AppResult.Success -> {
                val ride = rideResult.data

                when (val userResult = userRepository.getUserById(ride.ownerId)) {

                    is AppResult.Error -> userResult

                    is AppResult.Success -> {
                        AppResult.Success(
                            RideDetail(
                                ride = ride,
                                owner = userResult.data
                            )
                        )
                    }
                }
            }
        }
    }
}
