package com.trasladossolidarios.createride

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import com.trasladossolidarios.data.repository.RideRepositoryImpl
import com.trasladossolidarios.domain.enum.AnimalSpecies
import com.trasladossolidarios.domain.location.LocationTracker
import com.trasladossolidarios.domain.model.Animal
import com.trasladossolidarios.domain.model.Location
import com.trasladossolidarios.domain.model.Ride
import com.trasladossolidarios.network.cloudinary.datasource.CloudinaryDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateRideViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val cloudinaryDataSource: CloudinaryDataSource,
    private val rideRepositoryImpl: RideRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateRideUiState())
    val uiState: StateFlow<CreateRideUiState> = _uiState.asStateFlow()

    // Navegación del wizard
    fun nextStep() {
        _uiState.update { it.copy(currentStep = (it.currentStep + 1).coerceAtMost(2)) }
    }

    fun previousStep() {
        _uiState.update { it.copy(currentStep = (it.currentStep - 1).coerceAtLeast(0)) }
    }

    // Paso 0: Actualizar datos del Animal
    fun updateAnimalInfo(name: String, species: AnimalSpecies, description: String) {
        _uiState.update {
            it.copy(
                animalName = name,
                animalSpecies = species,
                animalDescription = description
            )
        }
    }

    fun setAnimalPhoto(uri: Uri) {
        _uiState.update { it.copy(animalPhotoUri = uri) }
    }

    // Paso 1: Actualizar detalles del traslado
    fun updateRideDetails(
        originAddress: String,
        destinationAddress: String,
        date: String,
        time: String,
        observations: String
    ) {
        _uiState.update {
            it.copy(
                originAddress = originAddress,
                destinationAddress = destinationAddress,
                date = date,
                time = time,
                observations = observations
            )
        }
    }

    // Geolocalización: Obtener ubicación actual para el origen
    fun fetchCurrentLocation() {
        viewModelScope.launch {
            val location = locationTracker.getCurrentLocation()
            if (location != null) {
                _uiState.update {
                    it.copy(
                        originLocation = location,
                        originAddress = location.address ?: "Ubicación actual"
                    )
                }
            }
        }
    }

    // Paso 2: Publicar viaje final (Cloudinary + Domain + Firestore a través del UseCase)
    fun publishRide() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                // 1. Subir la imagen a Cloudinary si existe una Uri capturada
                val photoUrl = _uiState.value.animalPhotoUri?.let { uri ->
                    cloudinaryDataSource.uploadImage(uri)
                }

                // 2. Construir el modelo de Dominio con Animal y Ride
                val animal = Animal(
                    name = _uiState.value.animalName,
                    species = _uiState.value.animalSpecies,
                    description = _uiState.value.animalDescription,
                    photoUrl = photoUrl
                )

                val transferInstant: Instant = try {
                    val dateStr = _uiState.value.date
                    val timeStr = _uiState.value.time
                    val dateTimeString = "$dateStr $timeStr"
                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    val localDateTime = LocalDateTime.parse(dateTimeString, formatter)
                    localDateTime.atZone(ZoneId.systemDefault()).toInstant()
                } catch (e: Exception) {
                    Instant.now() // Valor de respaldo por si el formato ingresado falla
                }

                val ride = Ride(
                    animal = animal,
                    origin = _uiState.value.originLocation ?: Location(0.0, 0.0, _uiState.value.originAddress),
                    destination = _uiState.value.destinationLocation ?: Location(0.0, 0.0, _uiState.value.destinationAddress),
                    dateOfTransfer = transferInstant,
                    notes = _uiState.value.observations,
                    acceptedApplicantId = null
                )

                // 3. Invocar el Caso de Uso de Dominio
                rideRepositoryImpl.createRide(ride)

                _uiState.update { it.copy(isLoading = false, isSuccess = true) }

            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message ?: "Error al publicar") }
            }
        }
    }
}