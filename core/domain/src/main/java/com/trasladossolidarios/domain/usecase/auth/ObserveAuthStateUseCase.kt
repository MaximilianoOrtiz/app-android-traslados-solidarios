package com.trasladossolidarios.domain.usecase.auth

import com.trasladossolidarios.domain.model.User
import com.trasladossolidarios.domain.repository.FirebaseAuthRepository
import kotlinx.coroutines.flow.Flow

class ObserveAuthStateUseCase(
    private val repository: FirebaseAuthRepository
) {
    operator fun invoke(): Flow<User?> {  // Return Flow<Boolean>
        return repository.observeAuthState()
    }
}