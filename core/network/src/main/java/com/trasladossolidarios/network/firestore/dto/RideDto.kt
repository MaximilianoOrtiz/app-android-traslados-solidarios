package com.trasladossolidarios.network.firestore.dto

import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.serialization.Serializable

@Serializable
data class RideDto(
    val id: String = "",
    val ownerId: String = "",
    val status: String = "",
    val animal: AnimalDto = AnimalDto(),
    val origin: LocationDto = LocationDto(),
    val destination: LocationDto = LocationDto(),
    val notes: String = "",
    val acceptedApplicantId: String? = null,
    val dateOfTransfer: Timestamp? = null,
)
