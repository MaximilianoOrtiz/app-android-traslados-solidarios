package com.trasladossolidarios.createride.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.trasladossolidarios.ui.theme.AppTheme
import com.trasladossolidarios.createride.CreateRideUiState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation

@Composable
fun StepRideDetails(
    uiState: CreateRideUiState,
    onUpdateDetails: (String, String, String, String, String) -> Unit,
    onFetchLocation: () -> Unit,
    onNext: () -> Unit
) {
    var originAddress by remember { mutableStateOf(uiState.originAddress) }
    var destinationAddress by remember { mutableStateOf(uiState.destinationAddress) }
    var date by remember { mutableStateOf(uiState.date) }
    var time by remember { mutableStateOf(uiState.time) }
    var observations by remember { mutableStateOf(uiState.observations) }

    LaunchedEffect(uiState.originAddress) {
        if (uiState.originAddress.isNotEmpty()) {
            originAddress = uiState.originAddress
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppTheme.spacing.md)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Punto de origen",
            style = AppTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = AppTheme.colors.onSurfaceVariant
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.xs)
        ) {
            OutlinedTextField(
                value = originAddress,
                onValueChange = { originAddress = it },
                placeholder = { Text("¿Dónde está el animal?") },
                modifier = Modifier.weight(1f),
                shape = AppTheme.shapes.medium
            )
            AssistChip(
                onClick = onFetchLocation,
                label = { Text("Mi ubicación", style = AppTheme.typography.labelMedium) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.MyLocation,
                        contentDescription = null,
                        tint = AppTheme.colors.secondary
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(AppTheme.spacing.md))

        Text(
            text = "Destino",
            style = AppTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = AppTheme.colors.onSurfaceVariant
        )
        OutlinedTextField(
            value = destinationAddress,
            onValueChange = { destinationAddress = it },
            placeholder = { Text("¿A dónde necesita llegar?") },
            modifier = Modifier.fillMaxWidth(),
            shape = AppTheme.shapes.medium
        )

        Spacer(modifier = Modifier.height(AppTheme.spacing.md))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.sm)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Fecha",
                    style = AppTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.onSurfaceVariant
                )
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    placeholder = { Text("dd/mm/aaaa") },
                    shape = AppTheme.shapes.medium
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Hora",
                    style = AppTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.onSurfaceVariant
                )
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    placeholder = { Text("00:00") },
                    shape = AppTheme.shapes.medium
                )
            }
        }

        Spacer(modifier = Modifier.height(AppTheme.spacing.md))

        Text(
            text = "Observaciones",
            style = AppTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = AppTheme.colors.onSurfaceVariant
        )
        OutlinedTextField(
            value = observations,
            onValueChange = { observations = it },
            placeholder = { Text("¿Algo importante para el traslado? Ej: necesita correa...") },
            modifier = Modifier.fillMaxWidth().height(120.dp),
            shape = AppTheme.shapes.medium
        )

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(AppTheme.spacing.lg))

        Button(
            onClick = {
                onUpdateDetails(originAddress, destinationAddress, date, time, observations)
                onNext()
            },
            modifier = Modifier.fillMaxWidth().height(54.dp),
            shape = AppTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                containerColor = AppTheme.colors.primary,
                contentColor = AppTheme.colors.onPrimary
            )
        ) {
            Text(
                text = "Continuar →", //fix
                style = AppTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}