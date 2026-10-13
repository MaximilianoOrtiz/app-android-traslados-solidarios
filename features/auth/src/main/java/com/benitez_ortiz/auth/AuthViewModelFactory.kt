package com.benitez_ortiz.auth

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import usecase.GetCurrentUserUseCase
import usecase.LoginUserUseCase
import usecase.LogoutUseCase
import usecase.RegisterUserUseCase

fun createAuthViewModelFactory(
    registerUser: RegisterUserUseCase,
    loginUser : LoginUserUseCase,
    logoutUser: LogoutUseCase,
    getCurrentUser: GetCurrentUserUseCase
): ViewModelProvider.Factory = viewModelFactory{
    initializer {
        AuthViewModel(registerUser, loginUser, logoutUser, getCurrentUser)
    }
}