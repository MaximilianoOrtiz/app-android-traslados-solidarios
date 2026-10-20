package com.trasladossolidarios.myrides

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MyRidesViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<MyRidesState>(MyRidesState.Idle)
    val uiState: StateFlow<MyRidesState> = _uiState.asStateFlow()
}