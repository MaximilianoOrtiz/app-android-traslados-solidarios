package com.trasladossolidarios.openrides.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.usecase.ride.GetRideDetailByIdUseCase
import com.trasladossolidarios.domain.usecase.volunteer.ApplyToRideUseCase
import com.trasladossolidarios.domain.usecase.volunteer.HasAppliedToRideUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed interface RideDetailEvent {
    data object ApplySuccess : RideDetailEvent
    data object ApplyError : RideDetailEvent
}

@HiltViewModel(assistedFactory = RideDetailViewModel.Factory::class)
class RideDetailViewModel @AssistedInject constructor(
    @Assisted private val rideId: String,
    private val getRideDetail: GetRideDetailByIdUseCase,
    private val applyToRide: ApplyToRideUseCase,
    private val hasAppliedToRide: HasAppliedToRideUseCase
): ViewModel(){

    @AssistedFactory
    interface Factory {
        fun create(rideId: String): RideDetailViewModel
    }

    private val _uiState = MutableStateFlow<RideDetailState>(RideDetailState.Loading)
    val uiState: StateFlow<RideDetailState> = _uiState.asStateFlow()

    private val _events = Channel<RideDetailEvent>()
    val events = _events.receiveAsFlow()

    init {
        load()
    }

    fun onRetry() = load()

    fun onAcceptClick() {
        val current = _uiState.value
        if (current is RideDetailState.Success) {
            _uiState.value = current.copy(isSheetOpen = true)
        }
    }

    fun onDismissSheet() {
        val current = _uiState.value
        if (current is RideDetailState.Success && !current.isSubmitting) {
            _uiState.value = current.copy(isSheetOpen = false)
        }
    }

    fun onSubmitApplication(message: String, contact: String) {
        val current = _uiState.value
        if (current !is RideDetailState.Success || current.isSubmitting) return

        _uiState.value = current.copy(isSubmitting = true)

        viewModelScope.launch {
            when (applyToRide(rideId, message, contact)) {
                is AppResult.Success -> {
                    _uiState.value = (_uiState.value as? RideDetailState.Success)
                        ?.copy(isSheetOpen = false, isSubmitting = false, hasApplied = true)
                        ?: RideDetailState.Loading
                    _events.send(RideDetailEvent.ApplySuccess)
                }
                is AppResult.Error -> {
                    _uiState.value = (_uiState.value as? RideDetailState.Success)
                        ?.copy(isSheetOpen = false, isSubmitting = false)
                        ?: RideDetailState.Loading
                    _events.send(RideDetailEvent.ApplyError)
                }
            }
        }
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = RideDetailState.Loading
            when (val result = getRideDetail(rideId)) {
                is AppResult.Success -> {
                    val applied = when (val appliedResult = hasAppliedToRide(rideId)) {
                        is AppResult.Success -> appliedResult.data
                        is AppResult.Error -> false
                    }
                    _uiState.value = RideDetailState.Success(
                        rideDetail = result.data,
                        hasApplied = applied
                    )
                }
                is AppResult.Error -> _uiState.value = RideDetailState.Error(result.error)
            }
        }
    }
}



