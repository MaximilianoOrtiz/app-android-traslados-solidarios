package com.trasladossolidarios.auth

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.trasladossolidarios.domain.usecase.auth.GetCurrentUserUseCase
import com.trasladossolidarios.domain.usecase.auth.LoginUserUseCase
import com.trasladossolidarios.domain.usecase.auth.LogoutUseCase
import com.trasladossolidarios.domain.usecase.auth.ObserveAuthStateUseCase
import com.trasladossolidarios.domain.usecase.auth.RegisterUserUseCase

fun createAuthViewModelFactory(
    registerUser: RegisterUserUseCase,
    loginUser: LoginUserUseCase,
    logoutUser: LogoutUseCase,
    getCurrentUser: GetCurrentUserUseCase,
    observeAuthState: ObserveAuthStateUseCase
): ViewModelProvider.Factory = viewModelFactory {
    initializer {
        AuthViewModel(registerUser, loginUser, logoutUser, getCurrentUser, observeAuthState)
    }
}