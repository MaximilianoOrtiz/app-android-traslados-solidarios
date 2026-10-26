package com.trasladossolidarios.network.firestore.dto

import com.google.firebase.Timestamp

data class RideDto(
    val ownerId: String = "",
    val status: String = "OPEN",
    val animal: AnimalDto = AnimalDto(),
    val origin: LocationDto = LocationDto(),
    val destination: LocationDto = LocationDto(),
    val notes: String = "",
    val acceptedApplicantId: String? = null,
    val dateOfTransfer: Timestamp? = null,
    val volunteers: Map<String, VolunteerDto> = emptyMap(),
)