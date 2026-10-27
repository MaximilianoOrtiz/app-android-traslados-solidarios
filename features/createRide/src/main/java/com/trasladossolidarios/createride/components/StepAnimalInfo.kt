package com.trasladossolidarios.createride.components

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult
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
import com.trasladossolidarios.ui.theme.AppTheme
import com.trasladossolidarios.createride.CreateRideUiState
import com.trasladossolidarios.domain.enum.AnimalSpecies

@Composable
fun StepAnimalInfo(
    uiState: CreateRideUiState,
    onUpdateInfo: (String, AnimalSpecies, String) -> Unit,
    onPhotoSelected: (Uri) -> Unit,
    onNext: () -> Unit
) {
    var name by remember { mutableStateOf(uiState.animalName) }
    var species by remember { mutableStateOf(uiState.animalSpecies) }
    var description by remember { mutableStateOf(uiState.animalDescription) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onPhotoSelected(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppTheme.spacing.md)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "FOTO DEL ANIMAL",
            style = AppTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = AppTheme.colors.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(AppTheme.spacing.xs))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.sm)
        ) {
            OutlinedCard(
                onClick = { /* Lógica de Cámara */ },
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

            OutlinedCard(
                onClick = { galleryLauncher.launch("image/*") },
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

        Spacer(modifier = Modifier.height(AppTheme.spacing.md))

        Text(
            text = "TIPO DE ANIMAL",
            style = AppTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = AppTheme.colors.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(AppTheme.spacing.xs))

        Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.xs)) {
            FilterChip(
                selected = species == AnimalSpecies.DOG,
                onClick = { species = AnimalSpecies.DOG },
                label = { Text("Perro", style = AppTheme.typography.bodyMedium) }
            )
            FilterChip(
                selected = species == AnimalSpecies.CAT,
                onClick = { species = AnimalSpecies.CAT },
                label = { Text("Gato", style = AppTheme.typography.bodyMedium) }
            )
            FilterChip(
                selected = species == AnimalSpecies.OTHER,
                onClick = { species = AnimalSpecies.OTHER },
                label = { Text("Otro", style = AppTheme.typography.bodyMedium) }
            )
        }

        Spacer(modifier = Modifier.height(AppTheme.spacing.md))

        Text(
            text = "Nombre del animal",
            style = AppTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = AppTheme.colors.onSurfaceVariant
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("¿Cómo se llama?") },
            modifier = Modifier.fillMaxWidth(),
            shape = AppTheme.shapes.medium
        )

        Spacer(modifier = Modifier.height(AppTheme.spacing.md))

        Text(
            text = "Descripción",
            style = AppTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = AppTheme.colors.onSurfaceVariant
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            placeholder = { Text("Contanos un poco sobre el animal...") },
            modifier = Modifier.fillMaxWidth().height(120.dp),
            shape = AppTheme.shapes.medium
        )

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(AppTheme.spacing.lg))

        Button(
            onClick = {
                onUpdateInfo(name, species, description)
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