package com.trasladossolidarios.di

import androidx.lifecycle.ViewModelProvider
import com.trasladossolidarios.repository.FirebaseAuthRepositoryImpl
import com.trasladossolidarios.auth.createAuthViewModelFactory
import com.trasladossolidarios.domain.repository.FirebaseAuthRepository
import com.trasladossolidarios.domain.usecase.auth.GetCurrentUserUseCase
import com.trasladossolidarios.domain.usecase.auth.LoginUserUseCase
import com.trasladossolidarios.domain.usecase.auth.LogoutUseCase
import com.trasladossolidarios.domain.usecase.auth.RegisterUserUseCase

class AppContainer {
    private val authRepository: FirebaseAuthRepository = FirebaseAuthRepositoryImpl()

    val authViewModelFactory: ViewModelProvider.Factory = createAuthViewModelFactory(
        registerUser = RegisterUserUseCase(authRepository),
        loginUser = LoginUserUseCase(authRepository),
        logoutUser = LogoutUseCase(authRepository),
        getCurrentUser = GetCurrentUserUseCase(authRepository)
    )
}