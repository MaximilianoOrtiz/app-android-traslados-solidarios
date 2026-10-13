package com.benitez_ortiz.ApMobileTP2

import androidx.lifecycle.ViewModelProvider
import auth.FirebaseAuthRepository
import com.benitez_ortiz.auth.createAuthViewModelFactory
import repository.AuthRepository
import usecase.GetCurrentUserUseCase
import usecase.LoginUserUseCase
import usecase.LogoutUseCase
import usecase.RegisterUserUseCase

class AppContainer {
    private val authRepository: AuthRepository = FirebaseAuthRepository()

    val authViewModelFactory: ViewModelProvider.Factory = createAuthViewModelFactory(
        registerUser = RegisterUserUseCase(authRepository),
        loginUser = LoginUserUseCase(authRepository),
        logoutUser = LogoutUseCase(authRepository),
        getCurrentUser = GetCurrentUserUseCase(authRepository)
    )
}