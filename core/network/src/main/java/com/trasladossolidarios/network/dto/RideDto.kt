package com.trasladossolidarios.network.dto

import java.util.Date

data class RideDto (
    val id: String = "",
    val userId: String = "",
    val pet: PetDto = PetDto(),

    val status: String = "",
    val imageUrl: String?,
    val volunteerId: String? = null,
    val dateTime: Date? = null,
    val specialNotes: String = "",

    //Puntos Geográficos
    val origin: LocationDto = LocationDto(),
    val destination: LocationDto = LocationDto(),
)