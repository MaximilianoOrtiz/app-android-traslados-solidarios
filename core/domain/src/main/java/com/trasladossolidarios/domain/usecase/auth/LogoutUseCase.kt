package com.trasladossolidarios.domain.usecase.auth

import com.trasladossolidarios.domain.repository.FirebaseAuthRepository
import javax.inject.Inject

class LogoutUseCase  @Inject constructor (private val authRepository: FirebaseAuthRepository){
    operator fun invoke() = authRepository.logout()
}