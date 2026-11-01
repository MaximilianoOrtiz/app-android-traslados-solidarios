package com.trasladossolidarios.openrides.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trasladossolidarios.components.PrimaryButton
import com.trasladossolidarios.components.RideDetailContent
import com.trasladossolidarios.components.toMessage
import com.trasladossolidarios.domain.enum.AnimalSpecies
import com.trasladossolidarios.domain.enum.RideStatus
import com.trasladossolidarios.domain.model.Animal
import com.trasladossolidarios.domain.model.Location
import com.trasladossolidarios.domain.model.Ride
import com.trasladossolidarios.domain.model.RideDetail
import com.trasladossolidarios.domain.model.User
import com.trasladossolidarios.openrides.R
import com.trasladossolidarios.ui.theme.ApMobileTP2Theme
import com.trasladossolidarios.ui.theme.AppTheme
import components.ErrorContent
import components.LoadingContent
import java.time.Instant


@Composable
fun RideDetailRoute(
    viewModel: RideDetailViewModel,
    onBack: () -> Unit,
    onApplied: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is RideDetailEvent.ApplySuccess -> showSuccessDialog = true
                is RideDetailEvent.ApplyError -> showErrorDialog = true
            }
        }
    }

    RideDetailScreen(
        uiState = uiState,
        onBack = onBack,
        onRetry = viewModel::onRetry,
        onAccept = viewModel::onAcceptClick,
        onDismissSheet = viewModel::onDismissSheet,
        onSubmitApplication = viewModel::onSubmitApplication
    )

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text(stringResource(R.string.ride_detail_apply_success_title)) },
            text = { Text(stringResource(R.string.ride_detail_apply_success_message)) },
            confirmButton = {
                TextButton(onClick = {
                    showSuccessDialog = false
                    onApplied()
                }) {
                    Text(stringResource(R.string.ride_detail_apply_success_ok))
                }
            }
        )
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text(stringResource(R.string.ride_detail_apply_error)) },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false }) {
                    Text(stringResource(R.string.ride_detail_apply_success_ok))
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideDetailScreen(
    uiState: RideDetailState,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    onAccept: () -> Unit = {},
    onDismissSheet: () -> Unit = {},
    onSubmitApplication: (String, String) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,

        bottomBar = {
            if (uiState is RideDetailState.Success) {
                Surface(
                    color = AppTheme.colors.background,
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .padding(AppTheme.spacing.md)
                    ) {
                        PrimaryButton(
                            text = stringResource(R.string.ride_detail_accept_action),
                            onClick = onAccept,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        when (uiState) {
            is RideDetailState.Loading -> LoadingContent(
                message = stringResource(R.string.ride_detail_loading),
                modifier = Modifier.padding(paddingValues)
            )

            is RideDetailState.Error -> ErrorContent(
                title = stringResource(R.string.ride_detail_error_title),
                message = uiState.error.toMessage(),
                onRetry = onRetry,
                modifier = Modifier.padding(paddingValues)
            )

            is RideDetailState.Success -> {
                RideDetailContent(
                    ride = uiState.rideDetail.ride,
                    owner = uiState.rideDetail.owner,
                    onBack = onBack,
                    modifier = Modifier.padding(paddingValues)
                )

                if (uiState.isSheetOpen) {
                    ApplyToRideSheet(
                        isSubmitting = uiState.isSubmitting,
                        onSubmit = onSubmitApplication,
                        onDismiss = onDismissSheet
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun RideDetailScreenPreview() {
    ApMobileTP2Theme {
        RideDetailScreen(
            uiState = RideDetailState.Success(
                RideDetail(
                    ride = Ride(
                        id = "1",
                        ownerId = "owner",
                        status = RideStatus.OPEN,
                        animal = Animal(
                            name = "Luna",
                            species = AnimalSpecies.CAT,
                            description = "Gatita gris tabulada, muy asustada. Fue encontrada bajo un auto en el barrio",
                            photoUrl = null
                        ),
                        origin = Location(
                            latitude = null,
                            longitude = null,
                            address = "Villa del Parque, CABA"
                        ),
                        destination = Location(
                            latitude = null,
                            longitude = null,
                            address = "Clínica Veterinaria Mascotas - Veterinaria - Palermo"
                        ),
                        notes = "Necesita transportadora obligatoriamente. Está asustada, requiere manejo suave.",
                        acceptedApplicantId = null,
                        dateOfTransfer = Instant.now().plusSeconds(86_400)
                    ),
                    owner = User(
                        uid = "owner",
                        firstName = "Carlos",
                        lastName = "Ruiz",
                        email = "carlos@email.com"
                    )
                )
            ),
            onBack = {},
            onRetry = {}
        )
    }
}
