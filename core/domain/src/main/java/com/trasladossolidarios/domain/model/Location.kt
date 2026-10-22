package com.trasladossolidarios.domain.model
data class Location (
    val latitude: Double,
    val longitude: Double,
    val addressName: String? = null
)