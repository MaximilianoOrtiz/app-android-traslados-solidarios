package usecase

import common.AppResult
import common.DomainError
import model.User
import repository.AuthRepository

class LoginUserUseCase (private val authRepository: AuthRepository){

    suspend operator fun invoke (email: String, password: String): AppResult<User>{
        //isBlack es true si el texto esta vacio o solo tiene espacios
        if (email.isBlank() || password.isBlank()){
            return AppResult.Error(DomainError.EmptyCredentials)
        }
        return authRepository.logIn(email.trim(), password)
    }
}