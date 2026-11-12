package com.trasladossolidarios.network.firestore.dto

import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.serialization.Serializable

@Serializable
data class VolunteerDto(
    val volunteerId: String = "",
    val name: String = "",
    val status: String = "PENDING",
    val message: String = "",
    val contact: String = "",
    val createdAt: Timestamp? = null
)
