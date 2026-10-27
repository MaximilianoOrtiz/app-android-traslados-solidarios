package com.trasladossolidarios.createride

import android.net.Uri
import com.trasladossolidarios.domain.enum.AnimalSpecies
import com.trasladossolidarios.domain.model.Location

data class CreateRideUiState(
    val currentStep: Int = 0, // 0: Animal, 1: Detalles del traslado, 2: Revisión

    // Información del Animal (Paso 0)
    val animalPhotoUri: Uri? = null,
    val animalName: String = "",
    val animalSpecies: AnimalSpecies = AnimalSpecies.DOG, //revisar fix
    val animalDescription: String = "",

    // Detalles del Traslado (Paso 1)
    val originAddress: String = "",
    val originLocation: Location? = null,
    val destinationAddress: String = "",
    val destinationLocation: Location? = null,
    val date: String = "",
    val time: String = "",
    val observations: String = "",

    // Estados de carga e interacción
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)