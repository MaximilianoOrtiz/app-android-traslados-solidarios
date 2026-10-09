package usecase

import model.User
import repository.AuthRepository

class LoginUserUseCase (private val authRepository: AuthRepository){

    suspend operator fun invoke (email: String, password: String): Result<User>{
        //isBlack es true si el texto esta vacio o solo tiene espacios
        if (email.isBlank() || password.isBlank()){
            return Result.failure(IllegalArgumentException("Complete el mail y la contraseña"))
        }
        return authRepository.logIn(email.trim(), password)
    }
}