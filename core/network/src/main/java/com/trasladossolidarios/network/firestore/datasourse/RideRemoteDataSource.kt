package com.trasladossolidarios.network.firestore.datasourse

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.trasladossolidarios.network.firestore.dto.RideDto
import com.trasladossolidarios.network.firestore.dto.RideResponseDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
// network/datasource/RideRemoteDataSource.kt
class RideRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
) {
    private val rides get() = firestore.collection("rides")

    fun observeOpenRides(): Flow<List<RideResponseDto>> =
        rides.whereEqualTo("status", "OPEN").observeRideDtos()

    fun observeRidesByOwner(ownerId: String): Flow<List<RideResponseDto>> =
        rides.whereEqualTo("ownerId", ownerId).observeRideDtos()

    suspend fun getRideById(rideId: String): RideResponseDto? {
        val document = rides.document(rideId).get().await()
        val dto = document.toObject<RideDto>() ?: return null
        return RideResponseDto(id = document.id, ride = dto)
    }

    private fun Query.observeRideDtos(): Flow<List<RideResponseDto>> = callbackFlow {
        val registration = addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (snapshot == null) {
                close(IllegalStateException("Firestore devolvió una consulta vacía."))
                return@addSnapshotListener
            }
            val dtos = snapshot.documents.mapNotNull { document ->
                document.toObject<RideDto>()?.let {
                    RideResponseDto(id = document.id, ride = it)
                }
            }
            trySend(dtos)
        }
        awaitClose { registration.remove() }
    }
}