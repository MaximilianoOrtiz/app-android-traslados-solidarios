package com.trasladossolidarios.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.common.DomainError
import com.trasladossolidarios.domain.model.User
import com.trasladossolidarios.domain.usecase.auth.GetCurrentUserUseCase
import com.trasladossolidarios.domain.usecase.auth.LoginUserUseCase
import com.trasladossolidarios.domain.usecase.auth.LogoutUseCase
import com.trasladossolidarios.domain.usecase.auth.ObserveAuthStateUseCase
import com.trasladossolidarios.domain.usecase.auth.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registerUser: RegisterUserUseCase,
    private val loginUser: LoginUserUseCase,
    private val logoutUser: LogoutUseCase,
    private val observeAuthState: ObserveAuthStateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeAuthState().collect { user ->
                if (user == null) {
                    resetForm()
                }
            }
        }
    }

    fun resetForm() {
        _uiState.value = AuthUiState()
    }

    fun toggleAuthMode() {
        _uiState.update {
            val newMode = if (it.mode == AuthMode.LOGIN) AuthMode.REGISTER else AuthMode.LOGIN
            it.copy(
                mode = newMode,
                error = null
            )
        }
    }

    fun onNameChange(name: String) {
        _uiState.update {
            it.copy(name = name, error = null)
        }
    }

    fun onSurnameChange(surname: String) {
        _uiState.update {
            it.copy(surname = surname, error = null)
        }
    }

    fun onEmailChange(email: String) {
        _uiState.update {
            it.copy(email = email, error = null)
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update {
            it.copy(password = password, error = null)
        }
    }

    fun register() {
        authenticate {
            registerUser(
                _uiState.value.name,
                _uiState.value.surname,
                _uiState.value.email.trim(),
                _uiState.value.password

            )
        }
    }

    fun login() {
        authenticate {
            loginUser(
                _uiState.value.email.trim(),
                _uiState.value.password
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUser()
            resetForm()
        }
    }

    fun onErrorShown() {
        _uiState.update { it.copy(error = null) }
    }

    private fun authenticate(
        action: suspend () -> AppResult<User>
    ) {
        if (_uiState.value.isLoading) return

        val current = _uiState.value

        if (current.email.isBlank() || current.password.isBlank()) {
            _uiState.update {
                it.copy(error = "Ingresá tu email y contraseña.")
            }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, error = null)
            }

            val result = try {
                action()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Ocurrió un error inesperado."
                    )
                }
                return@launch
            }

            when (result) {
                is AppResult.Success -> {
                    resetForm()
                }

                is AppResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = when (result.error) {
                                DomainError.InvalidMail -> "Email inválido."
                                DomainError.WeakPassword -> "La contraseña es muy débil."
                                DomainError.EmptyCredentials -> "Ingresá email y contraseña."
                                DomainError.EmailAlreadyInUse -> "El email ya está registrado."
                                DomainError.InvalidCredentials -> "Email o contraseña incorrectos."
                                DomainError.Network -> "Error de conexión."
                                DomainError.Unknow -> "Ocurrió un error inesperado."
                            }
                        )
                    }
                }
            }
        }
    }
}