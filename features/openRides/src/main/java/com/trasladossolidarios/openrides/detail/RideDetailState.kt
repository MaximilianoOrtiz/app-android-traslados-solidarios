package com.trasladossolidarios.openrides.detail

import com.trasladossolidarios.domain.common.DomainError
import com.trasladossolidarios.domain.model.Ride
import com.trasladossolidarios.domain.model.RideDetail

interface RideDetailState {

    data object Loading : RideDetailState
    data class Success(val rideDetail: RideDetail) : RideDetailState
    data class Error(val error: DomainError) : RideDetailState
}