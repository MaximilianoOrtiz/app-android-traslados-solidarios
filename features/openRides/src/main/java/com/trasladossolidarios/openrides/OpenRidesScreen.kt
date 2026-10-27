package com.trasladossolidarios.openrides

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trasladossolidarios.components.RideCard
import com.trasladossolidarios.ui.theme.AppTheme

@Composable
fun OpenRidesRouter(
    currentUserId: String,
    viewModel: OpenRidesViewModel,
    onRideClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(currentUserId) {
        viewModel.observeRides(currentUserId)
    }

    OpenRidesScreen(
        uiState = uiState,
        onRideClick = onRideClick,
        onRetry = { viewModel.onRetry(currentUserId) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenRidesScreen(
    uiState: OpenRidesState,
    modifier: Modifier = Modifier,
    onRideClick: (String) -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Traslados Disponibles",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppTheme.colors.primary,
                    titleContentColor = AppTheme.colors.onPrimary
                )
            )
        }
    ) { paddingValues ->
        val contentModifier = Modifier.padding(paddingValues)

        when (val state = uiState) {
            OpenRidesState.Loading -> {
                Box(
                    modifier = contentModifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is OpenRidesState.Error -> {
                Column(
                    modifier = contentModifier
                        .fillMaxSize()
                        .padding(AppTheme.spacing.lg),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No pudimos cargar los traslados.",
                        style = AppTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(AppTheme.spacing.sm))

                    Text(text = state.error.toString())

                    Spacer(modifier = Modifier.height(AppTheme.spacing.md))

                    Button(onClick = onRetry) {
                        Text("Reintentar")
                    }
                }
            }

            is OpenRidesState.Success -> {
                if (state.rides.isEmpty()) {
                    Box(
                        modifier = contentModifier
                            .fillMaxSize()
                            .padding(AppTheme.spacing.lg),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay traslados disponibles por el momento.",
                            style = AppTheme.typography.bodyLarge,
                            color = AppTheme.colors.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = contentModifier.fillMaxSize(),
                        contentPadding = PaddingValues(AppTheme.spacing.md),
                        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.sm + AppTheme.spacing.xs)
                    ) {
                        items(
                            items = state.rides,
                            key = { ride -> ride.id }
                        ) { ride ->
                            RideCard(
                                ride = ride,
                                onClick = { onRideClick(ride.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
