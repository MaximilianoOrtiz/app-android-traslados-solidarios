package com.trasladossolidarios.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trasladossolidarios.domain.common.AppResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.trasladossolidarios.domain.model.User
import com.trasladossolidarios.domain.usecase.auth.GetCurrentUserUseCase
import com.trasladossolidarios.domain.usecase.auth.LoginUserUseCase
import com.trasladossolidarios.domain.usecase.auth.LogoutUseCase
import com.trasladossolidarios.domain.usecase.auth.RegisterUserUseCase

class AuthViewModel (
    private val registerUser: RegisterUserUseCase,
    private val loginUser: LoginUserUseCase,
    private val logoutUser: LogoutUseCase,
    getCurrentUser: GetCurrentUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<AuthUiState>(
        getCurrentUser()?.let { AuthUiState.Success(it) } ?: AuthUiState.idle
    )
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun register(email: String, password: String) {
        authenticate { registerUser(email,password) }
    }

    fun login(email: String,password: String) {
        authenticate { loginUser(email,password)}
    }

    fun logout() {
        logoutUser()
        _uiState.value = AuthUiState.idle
    }

    fun onErrorShown() {
        _uiState.update { if (it is AuthUiState.Error) AuthUiState.idle else it }
    }

    private fun authenticate(action: suspend () -> AppResult<User>) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            _uiState.value = when (val  result = action ()) {
                is AppResult.Success -> AuthUiState.Success(result.data)
                is AppResult.Error -> AuthUiState.Error(result.error)
            }
        }
    }
}