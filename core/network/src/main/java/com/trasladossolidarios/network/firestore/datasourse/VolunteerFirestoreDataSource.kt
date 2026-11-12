package com.trasladossolidarios.network.firestore.datasourse

import com.google.firebase.firestore.FirebaseFirestore
import com.trasladossolidarios.network.firestore.dto.VolunteerDto
import kotlinx.coroutines.tasks.await

class VolunteerFirestoreDataSource constructor (
    private val firestore: FirebaseFirestore
){
    suspend fun applyToRide(
        rideid: String,
        volunteerDto: VolunteerDto
    ) {
        firestore.collection("rides")
            .document(rideid)
            .collection("volunteers")
            .document(volunteerDto.volunteerId)
            .set(volunteerDto)
            .await()
    }

    suspend fun hasApplied(rideId: String, volunteerId: String): Boolean {
        return firestore.collection("rides")
            .document(rideId)
            .collection("volunteers")
            .document(volunteerId)
            .get()
            .await()
            .exists()
    }
}