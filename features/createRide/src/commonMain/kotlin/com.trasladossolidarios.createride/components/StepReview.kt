package com.trasladossolidarios.createride.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.trasladossolidarios.ui.theme.AppTheme
import com.trasladossolidarios.createride.CreateRideUiState

@Composable
fun StepReview(
    uiState: CreateRideUiState,
    onPublish: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppTheme.spacing.md)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = AppTheme.colors.secondaryContainer),
            shape = AppTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Revisá todo antes de publicar. Una vez publicado, las personas de tu zona podrán verlo y ofrecerse.",
                modifier = Modifier.padding(AppTheme.spacing.md),
                style = AppTheme.typography.bodyMedium,
                color = AppTheme.colors.onSecondaryContainer
            )
        }

        Spacer(modifier = Modifier.height(AppTheme.spacing.md))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = AppTheme.shapes.large
        ) {
            Column(modifier = Modifier.padding(AppTheme.spacing.md)) {
                Text(
                    text = "Información del animal",
                    style = AppTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.xs))
                Text("Nombre: ${uiState.animalName.ifBlank { "Sin nombre" }}", style = AppTheme.typography.bodyMedium)
                Text("Tipo: ${uiState.animalSpecies}", style = AppTheme.typography.bodyMedium)
            }
        }

        Spacer(modifier = Modifier.height(AppTheme.spacing.sm))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = AppTheme.shapes.large
        ) {
            Column(modifier = Modifier.padding(AppTheme.spacing.md)) {
                Text(
                    text = "Traslado",
                    style = AppTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.xs))
                Text("Origen: ${uiState.originAddress.ifBlank { "No especificado" }}", style = AppTheme.typography.bodyMedium)
                Text("Destino: ${uiState.destinationAddress.ifBlank { "No especificado" }}", style = AppTheme.typography.bodyMedium)
                Text("Fecha/Hora: ${uiState.date} ${uiState.time}", style = AppTheme.typography.bodyMedium)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(AppTheme.spacing.md),
                color = AppTheme.colors.primary
            )
        } else {
            Button(
                onClick = onPublish,
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = AppTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.primary,
                    contentColor = AppTheme.colors.onPrimary
                )
            ) {
                Text(
                    text = "Publicar traslado", //fix
                    style = AppTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}