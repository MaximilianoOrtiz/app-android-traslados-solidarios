package com.trasladossolidarios.domain.usecase.auth

import com.trasladossolidarios.domain.model.User
import com.trasladossolidarios.domain.repository.FirebaseAuthRepository


//TODO: No se usa, se borra
class GetCurrentUserUseCase (private val authRepository: FirebaseAuthRepository){
    //Ver si hay una excepcion abierta
    operator fun invoke(): User? = authRepository.getCurrentUser()
}