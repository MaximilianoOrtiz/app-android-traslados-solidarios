package com.trasladossolidarios.network.firestore.datasourse

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.trasladossolidarios.network.firestore.dto.RideDto
import com.trasladossolidarios.network.firestore.dto.RideResponseDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RideRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
) {

    fun observeOpenRides(): Flow<List<RideResponseDto>> = callbackFlow {
        val registration = firestore
            .collection("rides")
            .whereEqualTo("status", "OPEN")
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot == null) {
                    close(
                        IllegalStateException(
                            "Firestore devolvió una consulta vacía."
                        )
                    )
                    return@addSnapshotListener
                }

                val rides = snapshot.documents.mapNotNull { document ->
                    val dto = document.toObject<RideDto>()
                        ?: return@mapNotNull null

                    RideResponseDto(
                        id = document.id,
                        ride = dto,
                    )
                }
                trySend(rides)
            }
        awaitClose {
            registration.remove()
        }
    }

    suspend fun getRideById(rideId: String): RideResponseDto? {
        val document = firestore
            .collection("rides")
            .document(rideId)
            .get()
            .await()

        val dto = document.toObject<RideDto>() ?: return null

        return RideResponseDto(
            id = document.id,
            ride = dto,
        )
    }

}