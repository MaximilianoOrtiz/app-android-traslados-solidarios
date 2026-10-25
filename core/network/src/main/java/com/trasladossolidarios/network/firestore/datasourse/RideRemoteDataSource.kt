package com.trasladossolidarios.network.firestore.datasourse

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.trasladossolidarios.network.firestore.dto.RideDto
import com.trasladossolidarios.network.result.NetworkResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RideRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
) {
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