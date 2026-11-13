package com.trasladossolidarios.domain.repository

import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.model.Volunteer

interface VolunteerRepository {

    suspend fun applyToRide(volunteer: Volunteer, rideId: String): AppResult<Unit>

    suspend fun hasAppliedToRide(rideId: String, volunteerId: String): AppResult<Boolean>
}