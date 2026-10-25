package com.trasladossolidarios.network.firestore.dto

import com.google.firebase.Timestamp

data class VolunteerDto(
    val applicantId: String = "",
    val volunteerName: String = "",
    val status: String = "PENDING",
    val message: String = "",
    val contact: String = "",
    val createdAt: Timestamp? = null,
)