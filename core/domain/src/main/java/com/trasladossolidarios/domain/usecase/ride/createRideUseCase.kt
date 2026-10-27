package com.trasladossolidarios.domain.usecase.ride

import com.trasladossolidarios.domain.model.Ride
import com.trasladossolidarios.domain.repository.RidesRepository
import javax.inject.Inject

class CreateRideUseCase @Inject constructor(
    private val ridesRepository: RidesRepository
) {
    suspend operator fun invoke(ride: Ride) {
        // Validaciones previas de reglas de negocio
        require(ride.animal.name.isNotBlank()) { "El nombre de la mascota es obligatorio" } //fix

        // Ejecución de la persistencia
        ridesRepository.createRide(ride)
    }
}