package com.trasladossolidarios.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.trasladossolidarios.domain.usecase.auth.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUser: LogoutUseCase
) : ViewModel(){

    private val _uiState = MutableStateFlow<ProfileState>(ProfileState.logout)
    val uiState: StateFlow<ProfileState> = _uiState.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            logoutUser()
        }
    }
}