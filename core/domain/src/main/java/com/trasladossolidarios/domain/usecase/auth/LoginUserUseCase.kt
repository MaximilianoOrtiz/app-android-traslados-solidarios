package com.trasladossolidarios.domain.usecase.auth

import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.common.DomainError
import com.trasladossolidarios.domain.model.User
import com.trasladossolidarios.domain.repository.FirebaseAuthRepository

class LoginUserUseCase (private val authRepository: FirebaseAuthRepository){

    suspend operator fun invoke (email: String, password: String): AppResult<User>{
        //isBlack es true si el texto esta vacio o solo tiene espacios
        if (email.isBlank() || password.isBlank()){
            return AppResult.Error(DomainError.EmptyCredentials)
        }
        return authRepository.logIn(email.trim(), password)
    }
}