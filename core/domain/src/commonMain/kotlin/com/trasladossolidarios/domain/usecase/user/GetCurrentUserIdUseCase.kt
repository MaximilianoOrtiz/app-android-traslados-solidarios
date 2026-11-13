package com.trasladossolidarios.domain.usecase.user

import com.trasladossolidarios.domain.repository.FirebaseAuthRepository

class GetCurrentUserIdUseCase constructor(
    private val authRepository: FirebaseAuthRepository,
) {
    operator fun invoke(): String? = authRepository.getCurrentUser()?.uid
}