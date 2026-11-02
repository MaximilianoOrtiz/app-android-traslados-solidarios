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
import android.util.Log
import com.trasladossolidarios.domain.repository.FirebaseAuthRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

@HiltViewModel
class CreateRideViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val cloudinaryDataSource: CloudinaryDataSource,
    private val rideRepositoryImpl: RideRepositoryImpl,
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateRideUiState())
    val uiState: StateFlow<CreateRideUiState> = _uiState.asStateFlow()

    // Navegación del wizard
    fun nextStep() {
        val state = _uiState.value
        when (state.currentStep) {
            0 -> {
                if (!state.isStep0Valid) {
                    _uiState.update {
                        it.copy(errorMessage = R.string.error_fill_animal_name_and_description)
                    }
                    return
                }
            }
            1 -> {
                if (!state.isStep1Valid) {
                    _uiState.update {
                        it.copy(errorMessage = R.string.error_fill_origin_destination_date_time)
                    }
                    return
                }
            }
        }

        _uiState.update { it.copy(currentStep = (it.currentStep + 1).coerceAtMost(2), errorMessage = null) }
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

    private var originSearchJob: Job? = null

    fun onOriginAddressChanged(address: String) {
        _uiState.update { it.copy(originAddress = address) }

        originSearchJob?.cancel()

        originSearchJob = viewModelScope.launch {
            if (address.length >= 3) {
                delay(400)
                val suggestions = locationTracker.searchAddresses(address)
                _uiState.update { it.copy(originSuggestions = suggestions) }
            } else {
                _uiState.update { it.copy(originSuggestions = emptyList()) }
            }
        }
    }

    fun onOriginSuggestionSelected(location: Location) {
        _uiState.update {
            it.copy(
                originAddress = location.address ?: "",
                originLocation = location,
                originSuggestions = emptyList()
            )
        }
    }

    private var searchJob: Job? = null

    fun onDestinationAddressChanged(address: String) {
        _uiState.update { it.copy(destinationAddress = address) }

        // Cancelar la búsqueda previa si el usuario sigue tipeando
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            if (address.length >= 3) {
                delay(400) // Espera 400ms tras dejar de escribir antes de consultar
                val suggestions = locationTracker.searchAddresses(address)
                Log.d("CreateRideViewModel", "Sugerencias encontradas: ${suggestions.size}")//fix
                _uiState.update { it.copy(destinationSuggestions = suggestions) }
            } else {
                _uiState.update { it.copy(destinationSuggestions = emptyList()) }
            }
        }
    }

    fun onDestinationSuggestionSelected(location: Location) {
        _uiState.update {
            it.copy(
                destinationAddress = location.address ?: "",
                destinationLocation = location,
                destinationSuggestions = emptyList() // Limpia las sugerencias al elegir una
            )
        }
    }

    // Paso 2: Publicar viaje final (Cloudinary + Domain + Firestore a través del UseCase)
    fun publishRide() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {

                val currentUser = firebaseAuthRepository.getCurrentUser()

                if (currentUser == null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = R.string.error_auth_required
                        )
                    }
                    return@launch
                }

                val ownerId = currentUser.uid
                Log.w("CreateRideViewModel", ownerId)
                val state = _uiState.value

                // 0. Validación de campos mínimos obligatorios
                if (state.animalName.isBlank() || state.originAddress.isBlank() || state.destinationAddress.isBlank()) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = R.string.error_fill_required_fields
                        )
                    }
                    return@launch
                }

                // 1. Subir imagen a Cloudinary si existe una Uri capturada
                val photoUrl = state.animalPhotoUri?.let { uri ->
                    cloudinaryDataSource.uploadImage(uri)
                }

                // 2. Construir los modelos de Dominio (Animal e Instant de transferencia)
                val animal = Animal(
                    name = state.animalName,
                    species = state.animalSpecies,
                    description = state.animalDescription,
                    photoUrl = photoUrl
                )

                val transferInstant: Instant = try {
                    val dateTimeString = "${state.date} ${state.time}"
                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    val localDateTime = LocalDateTime.parse(dateTimeString, formatter)
                    localDateTime.atZone(ZoneId.systemDefault()).toInstant()
                } catch (e: Exception) {
                    Instant.now()
                }

                val ride = Ride(
                    id= "",
                    ownerId = ownerId,
                    animal = animal,
                    origin = state.originLocation ?: Location(
                        latitude = 0.0,
                        longitude = 0.0,
                        address = state.originAddress
                    ),
                    destination = state.destinationLocation ?: Location(
                        latitude = 0.0,
                        longitude = 0.0,
                        address = state.destinationAddress
                    ),
                    dateOfTransfer = transferInstant,
                    notes = state.observations,
                    acceptedApplicantId = null
                )

                // 3. Invocar la abstracción de Dominio (UseCase o Repositorio inyectado por interfaz)
                rideRepositoryImpl.createRide(ride)
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = R.string.error_unexpected
                    )
                }
            }
        }
    }
}