package com.trasladossolidarios.domain.usecase.auth

import com.trasladossolidarios.domain.model.User
import com.trasladossolidarios.domain.repository.FirebaseAuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase  @Inject constructor (private val authRepository: FirebaseAuthRepository){
    //Ver si hay una excepcion abierta
    operator fun invoke(): User? = authRepository.getCurrentUser()
}