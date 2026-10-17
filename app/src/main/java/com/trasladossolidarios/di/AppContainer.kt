package com.trasladossolidarios.di

import com.trasladossolidarios.auth.AuthViewModel
import com.trasladossolidarios.repository.FirebaseAuthRepositoryImpl

import com.trasladossolidarios.domain.repository.FirebaseAuthRepository
import com.trasladossolidarios.domain.usecase.auth.GetCurrentUserUseCase
import com.trasladossolidarios.domain.usecase.auth.LoginUserUseCase
import com.trasladossolidarios.domain.usecase.auth.LogoutUseCase
import com.trasladossolidarios.domain.usecase.auth.ObserveAuthStateUseCase
import com.trasladossolidarios.domain.usecase.auth.RegisterUserUseCase
import com.trasladossolidarios.login.SessionViewModel

class AppContainer {
    private val authRepository: FirebaseAuthRepository = FirebaseAuthRepositoryImpl()

    val registerUser = RegisterUserUseCase(authRepository)
    val loginUser = LoginUserUseCase(authRepository)
    val logoutUser = LogoutUseCase(authRepository)

    val observeAuthStateUseCase =
        ObserveAuthStateUseCase(authRepository)

    fun authViewModelFactory() =
        AuthViewModel.factory(
            registerUser = registerUser,
            loginUser = loginUser,
            logoutUser = logoutUser,
            observeAuthState = observeAuthStateUseCase
        )

    fun sessionViewModelFactory() =
        SessionViewModel.factory(
            useCase = observeAuthStateUseCase
        )

}