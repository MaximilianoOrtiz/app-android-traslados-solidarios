package com.trasladossolidarios.domain.repository

import com.trasladossolidarios.domain.model.Location

interface LocationRepository {

    suspend fun getCurrentLocation(): Location?
    suspend fun searchAddresses(query: String): List<Location>
}