package com.trasladossolidarios.data.mapper

import com.trasladossolidarios.domain.enum.VolunteerStatus
import com.trasladossolidarios.domain.model.Volunteer
import com.trasladossolidarios.network.firestore.dto.VolunteerDto
import java.time.Instant

fun VolunteerDto.toDomain(
    volunteerId: String,
    rideId: String,
): Volunteer =
    Volunteer(
        volunteerId = volunteerId,
        rideId = rideId,
        name = volunteerName,
        status = status.toVolunteerStatus(),
        message = message,
        contact = contact,
        createdAt = createdAt
            ?.toDate()
            ?.toInstant()
            ?: Instant.EPOCH,
    )

fun Volunteer.toDto(): VolunteerDto =
    VolunteerDto(
        applicantId = volunteerId,
        volunteerName = name,
        status = status.name,
        message = message,
        contact = contact,
        createdAt = com.google.firebase.Timestamp(
            java.util.Date.from(createdAt)
        ),
    )

private fun String.toVolunteerStatus(): VolunteerStatus =
    runCatching {
        VolunteerStatus.valueOf(trim().uppercase())
    }.getOrDefault(VolunteerStatus.UNKNOWN)