package com.trasladossolidarios.domain.usecase.volunteer

import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.enum.VolunteerStatus
import com.trasladossolidarios.domain.model.Volunteer
import com.trasladossolidarios.domain.repository.FirebaseAuthRepository
import com.trasladossolidarios.domain.repository.VolunteerRepository
import java.time.Instant
import javax.inject.Inject

class ApplyToRideUseCase @Inject constructor(
    private val  volunteerRepository: VolunteerRepository,
    private  val authRepositoy : FirebaseAuthRepository
){
    suspend operator fun invoke(
        rideId: String,
        message: String,
        contact: String
    ): AppResult<Unit> {

        val user = authRepositoy.getCurrentUser()

        val volunteer = Volunteer(
            volunteerId = user?.uid ?: "",
            rideId = rideId,
            name = "${user?.firstName} ${user?.lastName}",
            status = VolunteerStatus.PENDING,
            message = message.trim(),
            contact = contact.trim(),
            createdAt = Instant.now()
        )

        return volunteerRepository.applyToRide(volunteer)
    }

}
