package com.trasladossolidarios.createride

import android.net.Uri
import androidx.annotation.StringRes
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
    val originSuggestions: List<Location> = emptyList(),
    val destinationLocation: Location? = null,
    val destinationSuggestions: List<Location> = emptyList(),
    val date: String = "",
    val time: String = "",
    val observations: String = "",

    // Estados de carga e interacción
    val isLoadingLocation: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    @StringRes val errorMessage: Int? = null
) {
    val isStep0Valid: Boolean
        get() = animalName.isNotBlank() && animalDescription.isNotBlank()

    val isStep1Valid: Boolean
        get() = originAddress.isNotBlank() && destinationAddress.isNotBlank() && date.isNotBlank() && time.isNotBlank()
}