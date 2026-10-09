package repository

import model.User

interface AuthRepository {
    //suspend función asincrona. Devuelven User o Error
    suspend fun register (email: String, password: String): Result<User>
    suspend fun logIn (email: String, password: String): Result<User>
    fun logout()
    fun getCurrentUser(): User?
}