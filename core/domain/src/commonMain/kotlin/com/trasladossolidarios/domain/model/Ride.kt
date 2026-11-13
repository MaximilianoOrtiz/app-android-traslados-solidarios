package com.trasladossolidarios.domain.model

import com.trasladossolidarios.domain.enum.RideStatus
import kotlin.time.Instant

data class Ride(
    val id: String,
    val ownerId: String = "",
    val status: RideStatus = RideStatus.OPEN,
    val animal: Animal,
    val origin: Location,
    val destination: Location,
    val notes: String,
    val acceptedApplicantId: String?,
    val dateOfTransfer: Instant,
)
