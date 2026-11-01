package com.trasladossolidarios.domain.usecase.volunteer

import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.common.DomainError
import com.trasladossolidarios.domain.repository.FirebaseAuthRepository
import com.trasladossolidarios.domain.repository.VolunteerRepository
import javax.inject.Inject

class HasAppliedToRideUseCase @Inject constructor(
    private val volunteerRepository: VolunteerRepository,
    private val authRepository: FirebaseAuthRepository
) {
    suspend operator fun invoke(rideId: String): AppResult<Boolean> {
        val user = authRepository.getCurrentUser()
            ?: return AppResult.Error(DomainError.InvalidCredentials)

        return volunteerRepository.hasAppliedToRide(rideId, user.uid)
    }
}
