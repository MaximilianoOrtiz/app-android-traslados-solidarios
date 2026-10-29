package com.trasladossolidarios.openrides.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.usecase.ride.ObserveAvailableRidesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OpenRidesViewModel @Inject constructor(
    private  val observeAvailableRides: ObserveAvailableRidesUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<OpenRidesState>(
            OpenRidesState.Loading
        )

    val uiState: StateFlow<OpenRidesState> = _uiState.asStateFlow()

    private var observeJob: Job? = null

    fun observeRides(currentUserId: String) {

        observeJob?.cancel()

        observeJob = viewModelScope.launch {
            _uiState.value = OpenRidesState.Loading

            observeAvailableRides(currentUserId).collect { result ->
                _uiState.value = when (result) {
                    is AppResult.Success ->
                        OpenRidesState.Success(
                            rides = result.data
                        )

                    is AppResult.Error ->
                        OpenRidesState.Error(
                            error = result.error
                        )
                }
            }
        }
    }

    fun onRetry(currentUserId: String) {
        observeRides(currentUserId)
    }
}