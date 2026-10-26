package com.trasladossolidarios.network.firestore.datasourse

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.trasladossolidarios.network.firestore.dto.UserDto
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserFirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore)
{

    suspend fun insert(uid: String, userDto: UserDto) {
        val ref = firestore.collection("users").document(uid)
        firestore.runTransaction { tx ->
            if (tx.get(ref).exists()) {
                tx.update(
                    ref, mapOf(
                        "email" to userDto.email,
                        "lastLoginAt" to FieldValue.serverTimestamp()
                    )
                )
            } else {
                tx.set(ref, userDto)
            }
        }.await()
    }
}