package com.trasladossolidarios.network.firestore.dto

import com.google.firebase.Timestamp

data class VolunteerDto(
    val volunteerId: String = "",
    val name: String = "",
    val status: String = "PENDING",
    val message: String = "",
    val contact: String = "",
    val rideOwnerId: String = "",
    val createdAt: Timestamp? = null
)