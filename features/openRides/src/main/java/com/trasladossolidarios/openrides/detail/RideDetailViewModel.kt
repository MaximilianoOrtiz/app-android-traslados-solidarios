package com.trasladossolidarios.openrides.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.usecase.ride.GetRideDetailByIdUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = RideDetailViewModel.Factory::class)
class RideDetailViewModel @AssistedInject constructor(
    @Assisted private val rideId: String,
    private val getRideDetail: GetRideDetailByIdUseCase
): ViewModel(){

    @AssistedFactory
    interface Factory {
        fun create(rideId: String): RideDetailViewModel
    }

    private val _uiState = MutableStateFlow<RideDetailState>(RideDetailState.Loading)
    val uiState: StateFlow<RideDetailState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun onRetry() = load()

    private fun load() {
        viewModelScope.launch {
            _uiState.value = RideDetailState.Loading
            _uiState.value = when (val result = getRideDetail(rideId)) {
                is AppResult.Success -> RideDetailState.Success(result.data)
                is AppResult.Error -> RideDetailState.Error(result.error)
            }
        }
    }
}



