package com.trasladossolidarios.domain.model

import com.trasladossolidarios.domain.enum.VolunteerStatus
import java.time.Instant

data class Volunteer(
    val volunteerId: String,      // uid del postulante
    val rideId: String,
    val name: String,
    val status: VolunteerStatus,
    val message: String,
    val contact: String,
    val createdAt: Instant,
)
