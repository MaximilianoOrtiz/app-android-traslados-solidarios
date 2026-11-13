package com.trasladossolidarios.network.firestore.dto

import dev.gitlive.firebase.firestore.BaseTimestamp
import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    // Sentinel de servidor: al escribirlo, Firestore lo reemplaza con la fecha
    // real (equivalente Kotlin-first de @ServerTimestamp del SDK de Android).
    val createdAt: BaseTimestamp = Timestamp.ServerTimestamp,
)
