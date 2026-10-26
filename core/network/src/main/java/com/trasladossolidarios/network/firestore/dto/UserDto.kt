package com.trasladossolidarios.network.firestore.dto

import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.Timestamp

data class UserDto(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null,
)