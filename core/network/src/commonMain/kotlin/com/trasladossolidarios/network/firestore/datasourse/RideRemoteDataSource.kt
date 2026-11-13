package com.trasladossolidarios.network.firestore.datasourse

import com.trasladossolidarios.network.firestore.dto.RideDto
import com.trasladossolidarios.network.firestore.dto.RideResponseDto
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
// network/datasource/RideRemoteDataSource.kt
class RideRemoteDataSource constructor(
    private val firestore: FirebaseFirestore,
) {
    private val rides get() = firestore.collection("rides")

    fun observeOpenRides(): Flow<List<RideResponseDto>> =
        rides.where { "status" equalTo "OPEN" }.observeRideDtos()

    fun observeRidesByOwner(ownerId: String): Flow<List<RideResponseDto>> =
        rides.where { "ownerId" equalTo ownerId }.observeRideDtos()

    suspend fun getRideById(rideId: String): RideResponseDto? {
        val document = rides.document(rideId).get()
        if (!document.exists) return null
        return RideResponseDto(id = document.id, ride = document.data<RideDto>())
    }

    private fun Query.observeRideDtos(): Flow<List<RideResponseDto>> = snapshots.map { snapshot ->
        snapshot.documents.mapNotNull { document ->
            if (document.exists) {
                RideResponseDto(id = document.id, ride = document.data<RideDto>())
            } else {
                null
            }
        }
    }

    suspend fun createRide(rideDto: RideDto) {
        val documentRef = firestore.collection("rides").document
        // Asignamos el ID autogenerado de Firestore al objeto antes de guardar
        val finalDto = rideDto.copy(id = documentRef.id)
        documentRef.set(finalDto)
    }

    /*
    suspend fun fetchByRequester(userId: String): NetworkResult<List<RideDto>> =
        try {
            val snapshot = firestore.collection(COLLECTION)
                .whereEqualTo("requesterId", userId)
                .get()
                .await()

            if (snapshot.metadata.isFromCache && snapshot.isEmpty) {
                // Sin conexión y sin datos en caché: no podemos saber si el usuario no tiene transferencias
                NetworkResult.NetworkError
            } else {
                NetworkResult.Success(
                    snapshot.documents.mapNotNull { doc ->
                        doc.toObject(RideDto::class.java)?.copy(id = doc.id)
                    }
                )
            }
        } catch (e: FirebaseFirestoreException) {
            when (e.code) {
                FirebaseFirestoreException.Code.UNAVAILABLE -> NetworkResult.NetworkError
                FirebaseFirestoreException.Code.PERMISSION_DENIED -> NetworkResult.PermissionDenied
                else -> NetworkResult.Unknown
            }
        }

    private companion object {
        const val COLLECTION = "Rides"
    }
    */

}
