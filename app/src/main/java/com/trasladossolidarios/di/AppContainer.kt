package com.trasladossolidarios.di

import com.trasladossolidarios.repository.FirebaseAuthRepositoryImpl

import com.trasladossolidarios.domain.repository.FirebaseAuthRepository
import com.trasladossolidarios.domain.usecase.auth.GetCurrentUserUseCase
import com.trasladossolidarios.domain.usecase.auth.LoginUserUseCase
import com.trasladossolidarios.domain.usecase.auth.LogoutUseCase
import com.trasladossolidarios.domain.usecase.auth.RegisterUserUseCase

class AppContainer {
    private val authRepository: FirebaseAuthRepository = FirebaseAuthRepositoryImpl()

    val registerUser = RegisterUserUseCase(authRepository)
    val loginUser = LoginUserUseCase(authRepository)
    val logoutUser = LogoutUseCase(authRepository)
    val getCurrentUser = GetCurrentUserUseCase(authRepository)

}