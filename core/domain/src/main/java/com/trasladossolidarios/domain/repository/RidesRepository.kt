package com.trasladossolidarios.domain.repository

import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.model.Ride
import kotlinx.coroutines.flow.Flow

interface RidesRepository {


    /** Lo que muestra la pantalla: siempre proviene de la base de datos local */
    fun observeMyRides(userId: String): Flow<List<Ride>>

    /** Descarga los datos más recientes del servidor y los guarda localmente */
    suspend fun refreshMyRides(userId: String): AppResult<Unit>


    suspend fun observeAvailableRides(currentUserId: String):  Flow<AppResult<List<Ride>>>

}