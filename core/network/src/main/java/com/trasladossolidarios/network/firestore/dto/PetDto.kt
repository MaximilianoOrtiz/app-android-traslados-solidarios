package com.trasladossolidarios.network.firestore.dto

data class PetDto (
    val type: String = "",
    val name: String = "",
    val size: String = "",
    val description: String = "",
    val fotoUrl: String? = null,
)