package com.trasladossolidarios.domain.repository

import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.model.Ride
import kotlinx.coroutines.flow.Flow

interface RidesRepository {

    fun observeRidesByOwner(userId: String): Flow<AppResult<List<Ride>>>

    suspend fun createRide(ride: Ride)
    suspend fun observeAvailableRides(currentUserId: String):  Flow<AppResult<List<Ride>>>

    /** Trae un traslado puntual por su id (para la pantalla de detalle) */
    suspend fun getRideById(rideId: String): AppResult<Ride>



}