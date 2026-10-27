package com.trasladossolidarios.openrides

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.TripOrigin
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trasladossolidarios.domain.model.Ride
import com.trasladossolidarios.ui.theme.AppTheme

@Composable
fun OpenRidesRouter(
    currentUserId: String,
    viewModel: OpenRidesViewModel,
    onRideClick: (String) -> Unit
) {
    val  uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                    containerColor =  AppTheme.colors.primary,
                    titleContentColor = AppTheme.colors.onPrimary

                )
            )
        }
    ){ paddingValues  ->
        // Contenido principal de la pantalla

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
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No pudimos cargar los traslados.",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = state.error.toString()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

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
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay traslados disponibles por el momento.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = contentModifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = state.rides,
                            key = { ride -> ride.id }
                        ) { ride ->
                            AvailableRideCard(
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

@Composable
private fun AvailableRideCard(
    ride: Ride,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = ride.animal.name.ifBlank {
                            "Animal sin nombre"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = ride.animal.species.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (ride.animal.description.isNotBlank()) {
                Text(
                    text = ride.animal.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            HorizontalDivider()

            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Default.TripOrigin,
                    contentDescription = "Origen",
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = "Origen",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = ride.origin.address,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Destino",
                    tint = MaterialTheme.colorScheme.tertiary
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = "Destino",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = ride.destination.address,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Ver traslado",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}