package com.trasladossolidarios.openrides.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    onAccept: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    RideDetailScreen(
        uiState = uiState,
        onBack = onBack,
        onRetry = viewModel::onRetry,
        onAccept = onAccept
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideDetailScreen(
    uiState: RideDetailState,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    onAccept: () -> Unit = {},
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

            is RideDetailState.Success -> RideDetailContent(
                ride = uiState.ride,
                onBack = onBack,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RideDetailScreenPreview() {
    ApMobileTP2Theme {
        RideDetailScreen(
            uiState = RideDetailState.Success(
                Ride(
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
                )
            ),
            onBack = {},
            onRetry = {}
        )
    }
}
