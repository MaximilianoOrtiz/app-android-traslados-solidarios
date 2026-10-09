package usecase

import model.User
import repository.AuthRepository

class GetCurrentUserUseCase (private val authRepository: AuthRepository){
    //Ver si hay una excepcion abierta
    operator fun invoke(): User? = authRepository.getCurrentUser()
}