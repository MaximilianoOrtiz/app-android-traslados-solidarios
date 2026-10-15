package com.trasladossolidarios.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.trasladossolidarios.domain.usecase.auth.ObserveAuthStateUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
class SessionViewModel(
    observeAuthState: ObserveAuthStateUseCase
) : ViewModel() {

    val state = observeAuthState()
        .map { user ->
            if (user == null) {
                SessionState.Unauthenticated
            } else {
                SessionState.Authenticated(user)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SessionState.Loading
        )

    companion object {
        fun factory(useCase: ObserveAuthStateUseCase) = viewModelFactory {
            initializer {
                SessionViewModel(useCase)
            }
        }
    }
}