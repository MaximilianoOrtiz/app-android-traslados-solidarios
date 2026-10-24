package com.trasladossolidarios.domain.model

data class Pet (
    val type: PetType,
    val name: String,
    val size: PetSize,
    val description: String,
    val fotoUrl: String? = null,
)