package com.trasladossolidarios.auth

import com.trasladossolidarios.domain.common.DomainError
import com.trasladossolidarios.domain.model.User

enum class AuthMode {
    LOGIN,
    REGISTER
}

data class AuthUiState(
    val mode: AuthMode = AuthMode.LOGIN,
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val surname: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)