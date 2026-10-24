package com.trasladossolidarios.domain.model

import java.time.Instant


data class Ride (
    val id: String,
    val userId: String,
    val pet: Pet,

    val status: RideStatus,
    val imageUrl: String?,
    val volunteerId: String? = null,
    val dateTime: Instant,
    val specialNotes: String,

    //Puntos Geográficos
    val origin: Location,
    val destination: Location,
)