package com.trasladossolidarios.domain.usecase.volunteer

import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.enum.VolunteerStatus
import com.trasladossolidarios.domain.model.RideDetail
import com.trasladossolidarios.domain.model.User
import com.trasladossolidarios.domain.model.Volunteer
import com.trasladossolidarios.domain.model.getFullName
import com.trasladossolidarios.domain.repository.FirebaseAuthRepository
import com.trasladossolidarios.domain.repository.UserRepository
import com.trasladossolidarios.domain.repository.VolunteerRepository
import java.time.Instant
import javax.inject.Inject

class ApplyToRideUseCase @Inject constructor(
    private val  volunteerRepository: VolunteerRepository,
    private  val authRepositoy : FirebaseAuthRepository,
    private  val userRepository: UserRepository
){
    suspend operator fun invoke(
        rideId: String,
        message: String,
        contact: String
    ): AppResult<Unit> {

        val userFirebase = authRepositoy.getCurrentUser()
        val user = userRepository.getUserById(userFirebase?.uid ?: "")

        return when(user) {
            is AppResult.Error -> user
            is AppResult.Success ->{
                val volunteer = Volunteer(
                    volunteerId = user.data.uid,
                    rideId = rideId,
                    name = user.data.getFullName(),
                    status = VolunteerStatus.PENDING,
                    message = message.trim(),
                    contact = contact.trim(),
                    createdAt = Instant.now()
                )
                volunteerRepository.applyToRide(volunteer, rideId)
            }
        }
    }

}
