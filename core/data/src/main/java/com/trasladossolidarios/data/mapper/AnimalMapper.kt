package com.trasladossolidarios.data.mapper

import com.trasladossolidarios.domain.enum.AnimalSpecies
import com.trasladossolidarios.domain.model.Animal
import com.trasladossolidarios.network.firestore.dto.AnimalDto

fun AnimalDto.toDomain(): Animal =
    Animal(
        name = name,
        species = species.toAnimalSpecies(),
        description = description,
        photoUrl = photoUrl,
    )

fun Animal.toDto(): AnimalDto =
    AnimalDto(
        name = name,
        species = species.name,
        description = description,
        photoUrl = photoUrl,
    )

private fun String.toAnimalSpecies(): AnimalSpecies =
    when (trim().uppercase()) {
        "PERRO" -> AnimalSpecies.DOG
        "GATO" -> AnimalSpecies.CAT
        "OTRO" -> AnimalSpecies.OTHER
        else -> AnimalSpecies.UNKNOWN
    }