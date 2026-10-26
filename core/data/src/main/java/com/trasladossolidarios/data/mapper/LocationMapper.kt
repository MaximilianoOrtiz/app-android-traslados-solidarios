package com.trasladossolidarios.data.mapper

import com.trasladossolidarios.domain.model.Location
import com.trasladossolidarios.network.firestore.dto.LocationDto

fun LocationDto.toDomain(): Location =
    Location(
        latitude = latitude,
        longitude = longitude,
        address = address,
    )

fun Location.toDto(): LocationDto =
    LocationDto(
        latitude = latitude,
        longitude = longitude,
        address = address,
    )