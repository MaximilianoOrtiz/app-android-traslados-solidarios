package com.trasladossolidarios.openrides

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trasladossolidarios.components.RideCard
import com.trasladossolidarios.components.toMessage
import com.trasladossolidarios.ui.theme.AppTheme
import com.trasladossolidarios.ui.theme.AppTheme.spacing
import components.ErrorContent
import components.LoadingContent

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
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ){
                        Text(
                            text = stringResource(R.string.open_rides_title),
                            style = AppTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(spacing.xs))
                        Text(
                            text = stringResource(R.string.open_rides_subtitle),
                            style = AppTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
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
                    LoadingContent(stringResource(R.string.open_rides_loading))
                }
            }

            is OpenRidesState.Error -> {
                ErrorContent(
                    title = stringResource(R.string.open_rides_error_title),
                    message = state.error.toMessage(),
                    onRetry = onRetry,
                    modifier = contentModifier
                        .fillMaxSize()
                        .padding(AppTheme.spacing.lg)
                )
            }

            is OpenRidesState.Success -> OpenRidesList(
                state = state,
                onRideClick = onRideClick,
                modifier = contentModifier
            )
        }
    }
}

@Composable
fun OpenRidesList(
    state: OpenRidesState.Success,
    onRideClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.rides.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(AppTheme.spacing.lg),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.open_rides_empty),
                style = AppTheme.typography.bodyLarge,
                color = AppTheme.colors.onSurfaceVariant
            )
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(AppTheme.spacing.md),
            verticalArrangement = Arrangement.spacedBy(
                AppTheme.spacing.sm + AppTheme.spacing.xs
            )
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
