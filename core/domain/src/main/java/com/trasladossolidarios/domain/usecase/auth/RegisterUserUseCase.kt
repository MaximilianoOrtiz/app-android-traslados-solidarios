package com.trasladossolidarios.domain.usecase.auth

import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.common.DomainError
import com.trasladossolidarios.domain.model.User
import com.trasladossolidarios.domain.repository.FirebaseAuthRepository
import com.trasladossolidarios.domain.repository.UserRepository
import javax.inject.Inject


class RegisterUserUseCase @Inject constructor(
    private val authRepository: FirebaseAuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): AppResult<User> {
        val cleanFirstName = firstName.trim()
        val cleanLastName = lastName.trim()
        val cleanEmail = email.trim()

        if (cleanFirstName.isBlank() || cleanLastName.isBlank()) {
            return AppResult.Error(DomainError.EmptyCredentials)
        }
        if (!EMAIL_REGEX.matches(cleanEmail)) {
            return AppResult.Error(DomainError.InvalidMail)
        }
        if (password.length < MIN_PASSWORD_LENGTH) {
            return AppResult.Error(DomainError.WeakPassword)
        }

        val authResult = authRepository.register(cleanEmail, password)
        if (authResult !is AppResult.Success) {
            return authResult
        }

        val authUser = authResult.data
        val fullUser = authUser.copy(
            firstName = cleanFirstName,
            lastName = cleanLastName
        )

        userRepository.saveUser(fullUser)

        return AppResult.Success(fullUser)
    }

    private companion object {
        val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        const val MIN_PASSWORD_LENGTH = 6
    }
}