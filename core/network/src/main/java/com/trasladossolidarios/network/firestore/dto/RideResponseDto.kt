package com.trasladossolidarios.network.firestore.dto

import kotlinx.serialization.Serializable

@Serializable
data class RideResponseDto (
    val id: String,
    val ride: RideDto,
)
