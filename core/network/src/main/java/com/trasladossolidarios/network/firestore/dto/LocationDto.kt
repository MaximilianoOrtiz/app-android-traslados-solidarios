package com.trasladossolidarios.network.firestore.dto

data class LocationDto (
    val latitude: Double? = null,
    val longitude: Double? = null,
    val addressName: String = ""
)