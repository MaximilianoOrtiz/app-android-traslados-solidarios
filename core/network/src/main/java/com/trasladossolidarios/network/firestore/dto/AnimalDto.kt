package com.trasladossolidarios.network.firestore.dto

data class AnimalDto(
    val type: String = "",
    val name: String = "",
    val species: String = "",
    val description: String = "",
    val photoUrl: String? = null,
)