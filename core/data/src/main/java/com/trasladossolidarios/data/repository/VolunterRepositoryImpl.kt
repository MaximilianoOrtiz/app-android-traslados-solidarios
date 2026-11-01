package com.trasladossolidarios.data.repository

import android.util.Log
import com.trasladossolidarios.data.mapper.toDto
import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.common.DomainError
import com.trasladossolidarios.domain.model.Volunteer
import com.trasladossolidarios.domain.repository.VolunteerRepository
import com.trasladossolidarios.network.firestore.datasourse.VolunteerFirestoreDataSource
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException


class VolunteerRepositoryImpl @Inject constructor(
    private val volunteerDataSource: VolunteerFirestoreDataSource,
) : VolunteerRepository {

    override suspend fun applyToRide(
        volunteer: Volunteer,
        rideId: String,
    ): AppResult<Unit> {
        return try {
            volunteerDataSource.applyToRide(
                rideId,
                volunteer.toDto()
            )

            AppResult.Success(Unit)

        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e("Huella", "Fallo al queres postularse", e)

            AppResult.Error(DomainError.Unknow)
        }
    }
}