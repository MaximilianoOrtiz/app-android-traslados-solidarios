package com.trasladossolidarios.domain.repository

import com.trasladossolidarios.domain.model.User

interface UserRepository {
    suspend fun saveUser(user: User): Result<Unit>
}