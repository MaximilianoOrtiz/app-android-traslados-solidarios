package com.trasladossolidarios.domain.location

import com.trasladossolidarios.domain.model.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}