package com.trasladossolidarios.data.mapper

import com.trasladossolidarios.domain.enum.VolunteerStatus
import com.trasladossolidarios.domain.model.Volunteer
import com.trasladossolidarios.network.firestore.dto.VolunteerDto
import dev.gitlive.firebase.firestore.Timestamp
import dev.gitlive.firebase.firestore.fromMilliseconds
import dev.gitlive.firebase.firestore.toMilliseconds
import kotlin.time.Instant

fun VolunteerDto.toDomain(rideId: String): Volunteer {
    return Volunteer(
        volunteerId = volunteerId,
        rideId = rideId,
        name = name,
        status = VolunteerStatus.valueOf(status),
        message = message,
        contact = contact,
        createdAt = createdAt
            ?.toMilliseconds()
            ?.let { Instant.fromEpochMilliseconds(it.toLong()) }
            ?: Instant.fromEpochMilliseconds(0)
    )
}

fun Volunteer.toDto(
): VolunteerDto {
    return VolunteerDto(
        volunteerId = volunteerId,
        name = name,
        status = status.name,
        message = message,
        contact = contact,
        createdAt = Timestamp.fromMilliseconds(createdAt.toEpochMilliseconds().toDouble())
    )
}
