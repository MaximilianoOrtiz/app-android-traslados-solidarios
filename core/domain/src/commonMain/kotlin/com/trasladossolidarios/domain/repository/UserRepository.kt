package com.trasladossolidarios.domain.repository

import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.model.User

interface UserRepository {
    suspend fun saveUser(user: User): Result<Unit>
    suspend fun getUserById(userId: String): AppResult<User>
}