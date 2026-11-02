package com.trasladossolidarios.myrides

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.usecase.ride.ObserveMyRidesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyRidesViewModel @Inject constructor(
    private val observeMyPublishedRides: ObserveMyRidesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyRidesState())
    val uiState: StateFlow<MyRidesState> = _uiState.asStateFlow()

    private var observeJob: Job? = null

    fun observeRides(currentUserId: String) {
        observeJob?.cancel()

        observeJob = viewModelScope.launch {
            _uiState.update { it.copy(published = PublishedRidesState.Loading) }

            observeMyPublishedRides(currentUserId).collect { result ->
                val published = when (result) {
                    is AppResult.Success -> PublishedRidesState.Success(rides = result.data)
                    is AppResult.Error -> PublishedRidesState.Error(error = result.error)
                }
                _uiState.update { it.copy(published = published) }
            }
        }
    }

    fun onTabSelected(tab: MyRidesTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun onRetry(currentUserId: String) {
        observeRides(currentUserId)
    }
}