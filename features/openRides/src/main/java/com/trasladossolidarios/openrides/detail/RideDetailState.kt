package com.trasladossolidarios.openrides.detail

import com.trasladossolidarios.domain.common.DomainError
import com.trasladossolidarios.domain.model.Ride

interface RideDetailState {

    data object Loading : RideDetailState
    data class Success(val ride: Ride) : RideDetailState
    data class Error(val error: DomainError) : RideDetailState
}