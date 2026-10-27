package com.trasladossolidarios.createride

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trasladossolidarios.ui.theme.AppTheme
import com.trasladossolidarios.createride.components.StepAnimalInfo
import com.trasladossolidarios.createride.components.StepRideDetails
import com.trasladossolidarios.createride.components.StepReview
import com.trasladossolidarios.domain.enum.AnimalSpecies

@Composable
fun CreateRideRouter(
    onBackClick: () -> Unit,
    onRideCreatedSuccess: () -> Unit,
    viewModel: CreateRideViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onRideCreatedSuccess()
        }
    }

    CreateRideScreen(
        uiState = uiState,
        onBackClick = {
            if (uiState.currentStep > 0) {
                viewModel.previousStep()
            } else {
                onBackClick()
            }
        },
        onUpdateAnimalInfo = viewModel::updateAnimalInfo,
        onPhotoSelected = viewModel::setAnimalPhoto,
        onUpdateRideDetails = viewModel::updateRideDetails,
        onFetchLocation = viewModel::fetchCurrentLocation,
        onNextStep = viewModel::nextStep,
        onPublishRide = viewModel::publishRide
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRideScreen(
    uiState: CreateRideUiState,
    onBackClick: () -> Unit,
    onUpdateAnimalInfo: (String, AnimalSpecies, String) -> Unit,
    onPhotoSelected: (Uri) -> Unit,
    onUpdateRideDetails: (String, String, String, String, String) -> Unit,
    onFetchLocation: () -> Unit,
    onNextStep: () -> Unit,
    onPublishRide: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Necesito un traslado",
                        style = AppTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppTheme.colors.surface,
                    titleContentColor = AppTheme.colors.onSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TabRow(
                selectedTabIndex = uiState.currentStep,
                containerColor = Color.Transparent,
                contentColor = AppTheme.colors.secondary
            ) {
                Tab(
                    selected = uiState.currentStep == 0,
                    onClick = { },
                    text = { Text("Información del animal", style = AppTheme.typography.labelMedium) }
                )
                Tab(
                    selected = uiState.currentStep == 1,
                    onClick = { },
                    text = { Text("Detalles del traslado", style = AppTheme.typography.labelMedium) }
                )
                Tab(
                    selected = uiState.currentStep == 2,
                    onClick = { },
                    text = { Text("Revisión", style = AppTheme.typography.labelMedium) }
                )
            }

            Spacer(modifier = Modifier.height(AppTheme.spacing.md))

            when (uiState.currentStep) {
                0 -> StepAnimalInfo(
                    uiState = uiState,
                    onUpdateInfo = onUpdateAnimalInfo,
                    onPhotoSelected = onPhotoSelected,
                    onNext = onNextStep
                )
                1 -> StepRideDetails(
                    uiState = uiState,
                    onUpdateDetails = onUpdateRideDetails,
                    onFetchLocation = onFetchLocation,
                    onNext = onNextStep
                )
                2 -> StepReview(
                    uiState = uiState,
                    onPublish = onPublishRide
                )
            }
        }
    }
}