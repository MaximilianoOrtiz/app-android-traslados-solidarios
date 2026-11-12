package com.trasladossolidarios.network.firestore.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto (
    val latitude: Double? = null,
    val longitude: Double? = null,
    val address: String = ""
)
