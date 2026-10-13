package repository

import common.AppResult
import model.User

interface AuthRepository {
    //suspend función asincrona. Devuelven User o Error
    suspend fun register (email: String, password: String): AppResult<User>
    suspend fun logIn (email: String, password: String): AppResult<User>
    fun logout()
    fun getCurrentUser(): User?
}