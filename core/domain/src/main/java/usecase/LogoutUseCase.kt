package usecase

import repository.AuthRepository

class LogoutUseCase (private val authRepository: AuthRepository){
    operator fun invoke() = authRepository.logout()
}