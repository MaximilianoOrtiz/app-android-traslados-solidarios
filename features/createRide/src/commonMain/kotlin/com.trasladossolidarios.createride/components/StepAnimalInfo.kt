package com.trasladossolidarios.createride.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.trasladossolidarios.createride.CreateRideUiState
import com.trasladossolidarios.createride.platform.rememberImagePicker
import com.trasladossolidarios.domain.enum.AnimalSpecies
import com.trasladossolidarios.ui.theme.AppTheme

@Composable
fun StepAnimalInfo(
    uiState: CreateRideUiState,
    onUpdateInfo: (String, AnimalSpecies, String) -> Unit,
    onPhotoSelected: (String) -> Unit,
    onNext: () -> Unit
) {
    val imagePicker = rememberImagePicker(onImagePicked = onPhotoSelected)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppTheme.spacing.md)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.md)
    ) {
        // --- 1. FOTO DEL ANIMAL ---
        Text(
            text = "FOTO DEL ANIMAL",//fix
            style = AppTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = AppTheme.colors.onSurfaceVariant
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.sm)
        ) {
            // Botón Tomar Foto (Cámara)
            OutlinedCard(
                onClick = { imagePicker.launchCamera() },
                modifier = Modifier.weight(1f).height(100.dp),
                shape = AppTheme.shapes.medium
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = null,
                        tint = AppTheme.colors.secondary
                    )
                    Text("Tomar foto", style = AppTheme.typography.bodyMedium)
                }
            }

            // Botón Elegir de Galería
            OutlinedCard(
                onClick = { imagePicker.launchGallery() },
                modifier = Modifier.weight(1f).height(100.dp),
                shape = AppTheme.shapes.medium
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoLibrary,
                        contentDescription = null,
                        tint = AppTheme.colors.secondary
                    )
                    Text("Elegir de galería", style = AppTheme.typography.bodyMedium)
                }
            }
        }

        // --- 2. TIPO DE ANIMAL ---
        Text(
            text = "TIPO DE ANIMAL *",
            style = AppTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = AppTheme.colors.onSurfaceVariant
        )

        Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.xs)) {
            AnimalSpecies.entries.forEach { speciesItem ->
                FilterChip(
                    selected = uiState.animalSpecies == speciesItem,
                    onClick = {
                        onUpdateInfo(uiState.animalName, speciesItem, uiState.animalDescription)
                    },
                    label = {
                        val labelText = when (speciesItem) {
                            AnimalSpecies.DOG -> "Perro"
                            AnimalSpecies.CAT -> "Gato"
                            else -> "Otro"
                        }
                        Text(labelText, style = AppTheme.typography.bodyMedium)
                    }
                )
            }
        }

        // --- 3. NOMBRE DEL ANIMAL ---
        Column {
            Text(
                text = "Nombre del animal *",
                style = AppTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = AppTheme.colors.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(AppTheme.spacing.xs))
            OutlinedTextField(
                value = uiState.animalName,
                onValueChange = { newName ->
                    onUpdateInfo(newName, uiState.animalSpecies, uiState.animalDescription)
                },
                placeholder = { Text("¿Cómo se llama?") },
                modifier = Modifier.fillMaxWidth(),
                shape = AppTheme.shapes.medium,
                singleLine = true
            )
        }

        // --- 4. DESCRIPCIÓN ---
        Column {
            Text(
                text = "Descripción *",
                style = AppTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = AppTheme.colors.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(AppTheme.spacing.xs))
            OutlinedTextField(
                value = uiState.animalDescription,
                onValueChange = { newDesc ->
                    onUpdateInfo(uiState.animalName, uiState.animalSpecies, newDesc)
                },
                placeholder = { Text("Contanos un poco sobre el animal...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = AppTheme.shapes.medium
            )
        }

        Spacer(modifier = Modifier.height(AppTheme.spacing.md))

        // --- 5. BOTÓN CONTINUAR ---
        Button(
            onClick = onNext,
            enabled = uiState.isStep0Valid,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
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