package com.trasladossolidarios.auth

import com.trasladossolidarios.domain.common.DomainError
import com.trasladossolidarios.domain.model.User

sealed interface AuthUiState {
    data object idle : AuthUiState
    data object Loading : AuthUiState
    data class Success (val user : User): AuthUiState
    data class Error (val error : DomainError) : AuthUiState
}