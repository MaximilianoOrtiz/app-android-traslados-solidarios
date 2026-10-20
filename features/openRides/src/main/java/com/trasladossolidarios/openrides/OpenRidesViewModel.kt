package com.trasladossolidarios.openrides

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class OpenRidesViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<OpenRidesState>(OpenRidesState.Idle)
    val uiState: StateFlow<OpenRidesState> = _uiState.asStateFlow()
}