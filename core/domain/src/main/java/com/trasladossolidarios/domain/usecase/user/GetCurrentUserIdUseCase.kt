package com.trasladossolidarios.domain.usecase.user

import com.trasladossolidarios.domain.repository.FirebaseAuthRepository
import javax.inject.Inject

class GetCurrentUserIdUseCase @Inject constructor(
    private val authRepository: FirebaseAuthRepository,
) {
    operator fun invoke(): String? = authRepository.getCurrentUser()?.uid
}