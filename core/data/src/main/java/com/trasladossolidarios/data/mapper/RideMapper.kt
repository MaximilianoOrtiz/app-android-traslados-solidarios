package com.trasladossolidarios.data.mapper

import com.trasladossolidarios.domain.enum.RideStatus
import com.trasladossolidarios.domain.model.Ride
import com.trasladossolidarios.network.firestore.dto.RideDto
import java.time.Instant

fun RideDto.toDomain(id: String): Ride =
    Ride(
        id = id,
        ownerId = ownerId,
        status = runCatching { RideStatus.valueOf(status)}.getOrDefault(RideStatus.OPEN),
        animal = animal.toDomain(),
        origin = origin.toDomain(),
        destination = destination.toDomain(),
        notes = notes,
        acceptedApplicantId = acceptedApplicantId,
        dateOfTransfer = dateOfTransfer
            ?.toDate()
            ?.toInstant()
            ?: Instant.EPOCH,
        volunteers = volunteers.map { (volunteerId, dto) ->
            dto.toDomain(
                volunteerId = volunteerId,
                rideId = id,
            )
        },
    )

fun Ride.toDto(): RideDto =
    RideDto(
        id = id ?: "",
        ownerId = ownerId,
        status = status.name,
        animal = animal.toDto(),
        origin = origin.toDto(),
        destination = destination.toDto(),
        notes = notes,
        acceptedApplicantId = acceptedApplicantId,
        dateOfTransfer = com.google.firebase.Timestamp(
            java.util.Date.from(dateOfTransfer)
        ),
        volunteers = volunteers.associate { volunteer ->
            volunteer.volunteerId to volunteer.toDto()
        },
    )

private fun String.toRideStatus(): RideStatus =
    runCatching {
        RideStatus.valueOf(trim().uppercase())
    }.getOrDefault(RideStatus.UNKNOWN)