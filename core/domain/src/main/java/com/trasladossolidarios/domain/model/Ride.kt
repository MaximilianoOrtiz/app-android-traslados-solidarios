package com.trasladossolidarios.domain.model

import com.trasladossolidarios.domain.enum.RideStatus
import java.time.Instant

data class Ride(
    val id: String,
    val ownerId: String,
    val status: RideStatus,
    val animal: Animal,
    val origin: Location,
    val destination: Location,
    val notes: String,
    val acceptedVolunteerId: String?,
    val dateOfTransfer: Instant,
    val volunteers: List<Volunteer> = emptyList(),
)
