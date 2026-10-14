package com.trasladossolidarios.auth

import common.DomainError
import model.User

sealed interface AuthUiState {
    data object idle : AuthUiState
    data object Loading : AuthUiState
    data class Success (val user : User): AuthUiState
    data class Error (val error : DomainError) : AuthUiState
}