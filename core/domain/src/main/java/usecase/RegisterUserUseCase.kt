package usecase

import model.User
import repository.AuthRepository

//Uso inyección de dependencias
class RegisterUserUseCase (private val authRepository : AuthRepository){
    //llama al objeto como si fuera funcion
    suspend operator fun invoke(email: String, password: String) : Result<User> {
        //elimina espacios
        val cleanEmail = email.trim ()
        //devuelve true si el patron de texto es correcto
        if (!EMAIL_REGEX.matches(cleanEmail)) {
            return Result.failure(IllegalArgumentException("El mail no es valido"))
        }
        if (password.length < MIN_PASSWORD_LENGTH) {
            return Result.failure(IllegalArgumentException("La contraseña debe tener al menos $MIN_PASSWORD_LENGTH caracteres"))
        }
        return authRepository.register(cleanEmail, password)
    }

    private companion object {
        //patron de texto
        val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}\$")
        const val MIN_PASSWORD_LENGTH = 6
    }
}