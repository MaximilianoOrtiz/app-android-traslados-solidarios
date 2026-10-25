package com.trasladossolidarios.domain.model

import com.trasladossolidarios.domain.enum.AnimalSpecies

data class Animal(
    val name: String,
    val species: AnimalSpecies,
    val description: String,
    val photoUrl: String?,
)