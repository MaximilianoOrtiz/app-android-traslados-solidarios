package com.trasladossolidarios.domain.usecase.auth

import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.common.DomainError
import com.trasladossolidarios.domain.model.User
import com.trasladossolidarios.domain.repository.FirebaseAuthRepository

//Uso inyección de dependencias
class RegisterUserUseCase (private val authRepository : FirebaseAuthRepository){
    //llama al objeto como si fuera funcion
    suspend operator fun invoke(email: String, password: String) : AppResult<User> {
        //elimina espacios
        val cleanEmail = email.trim ()
        //devuelve true si el patron de texto es correcto
        if (!EMAIL_REGEX.matches(cleanEmail)) {
            return AppResult.Error(DomainError.InvalidMail)
        }
        if (password.length < MIN_PASSWORD_LENGTH) {
            return AppResult.Error(DomainError.WeakPassword)
        }
        return authRepository.register(cleanEmail, password)
    }

    private companion object {
        //patron de texto
        val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        const val MIN_PASSWORD_LENGTH = 6
    }
}