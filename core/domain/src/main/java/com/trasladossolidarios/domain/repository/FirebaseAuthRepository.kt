package com.trasladossolidarios.domain.repository

import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.model.User
import kotlinx.coroutines.flow.Flow


interface FirebaseAuthRepository {
    //suspend función asincrona. Devuelven User o Error
    suspend fun register (email: String, password: String): AppResult<User>
    suspend fun logIn (email: String, password: String): AppResult<User>
    fun logout()
    fun getCurrentUser(): User?

    fun observeAuthState(): Flow<User?>
}