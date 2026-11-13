package com.trasladossolidarios.domain.usecase.auth

import com.trasladossolidarios.domain.repository.FirebaseAuthRepository

class LogoutUseCase  constructor (private val authRepository: FirebaseAuthRepository){
    operator suspend fun invoke() = authRepository.logout()
}