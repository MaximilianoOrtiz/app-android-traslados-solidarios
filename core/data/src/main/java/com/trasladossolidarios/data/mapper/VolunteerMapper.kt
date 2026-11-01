package com.trasladossolidarios.data.mapper

import com.google.firebase.Timestamp
import com.trasladossolidarios.domain.enum.VolunteerStatus
import com.trasladossolidarios.domain.model.Volunteer
import com.trasladossolidarios.network.firestore.dto.VolunteerDto
import java.time.Instant
import java.util.Date

fun VolunteerDto.toDomain(rideId: String): Volunteer {
    return Volunteer(
        volunteerId = volunteerId,
        rideId = rideId,
        name = name,
        status = VolunteerStatus.valueOf(status),
        message = message,
        contact = contact,
        createdAt = createdAt?.toDate()
            ?.toInstant()
            ?: Instant.EPOCH
    )
}

fun Volunteer.toDto(
    rideOwnerId: String,
): VolunteerDto {
    return VolunteerDto(
        volunteerId = volunteerId,
        name = name,
        status = status.name,
        message = message,
        contact = contact,
        rideOwnerId = rideOwnerId,
        createdAt = Timestamp(Date.from(createdAt))
    )
}
