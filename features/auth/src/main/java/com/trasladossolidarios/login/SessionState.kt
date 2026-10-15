package com.trasladossolidarios.login

import com.trasladossolidarios.domain.model.User

sealed interface SessionState {
    data object Loading : SessionState
    data object Unauthenticated : SessionState
    data class Authenticated(val user: User) : SessionState
}