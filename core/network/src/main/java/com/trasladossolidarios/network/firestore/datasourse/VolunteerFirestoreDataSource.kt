package com.trasladossolidarios.network.firestore.datasourse

import com.google.firebase.firestore.FirebaseFirestore
import com.trasladossolidarios.network.firestore.dto.VolunteerDto
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class VolunteerFirestoreDataSource @Inject constructor (
    private val firestore: FirebaseFirestore
){
    suspend fun applyToRide(
        volunteerDto: VolunteerDto
    ) {
        firestore.collection("rides")
            .document(volunteerDto.rideOwnerId)
            .collection("volunteers")
            .document(volunteerDto.volunteerId)
            .set(volunteerDto)
            .await()
    }
}