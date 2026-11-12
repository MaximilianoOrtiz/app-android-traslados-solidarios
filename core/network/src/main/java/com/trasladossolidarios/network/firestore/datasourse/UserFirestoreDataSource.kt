package com.trasladossolidarios.network.firestore.datasourse

import com.trasladossolidarios.network.firestore.dto.UserDto
import dev.gitlive.firebase.firestore.FieldValue
import dev.gitlive.firebase.firestore.FirebaseFirestore

class UserFirestoreDataSource constructor(
    private val firestore: FirebaseFirestore)
{

    suspend fun insert(uid: String, userDto: UserDto) {
        val ref = firestore.collection("users").document(uid)
        firestore.runTransaction {
            if (get(ref).exists) {
                updateFields(ref) {
                    "email" to userDto.email
                    "lastLoginAt" to FieldValue.serverTimestamp
                }
            } else {
                set(ref, UserDto.serializer(), userDto)
            }
        }
    }

    suspend fun getById(uid: String): UserDto? {
        val document = firestore
            .collection("users")
            .document(uid)
            .get()

        if (!document.exists) return null
        return document.data<UserDto>()
    }
}
