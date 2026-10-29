package com.trasladossolidarios.openrides.list

import com.trasladossolidarios.domain.common.DomainError
import com.trasladossolidarios.domain.model.Ride

interface OpenRidesState {

    data object Loading : OpenRidesState

    data class Success(
        val rides: List<Ride>
    ) : OpenRidesState

    data class Error(
        val error: DomainError
    ) : OpenRidesState

}