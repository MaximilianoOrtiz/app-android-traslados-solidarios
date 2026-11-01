package com.trasladossolidarios.openrides.detail

import com.trasladossolidarios.domain.common.DomainError
import com.trasladossolidarios.domain.model.Ride
import com.trasladossolidarios.domain.model.RideDetail

interface RideDetailState {

    data object Loading : RideDetailState
    data class Success(
        val rideDetail: RideDetail,
        val isSheetOpen: Boolean = false,
        val isSubmitting: Boolean = false
    ) : RideDetailState
    data class Error(val error: DomainError) : RideDetailState
}