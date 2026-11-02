package com.trasladossolidarios.createride.components

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.trasladossolidarios.createride.CreateRideUiState
import com.trasladossolidarios.domain.enum.AnimalSpecies
import com.trasladossolidarios.ui.theme.AppTheme
import java.io.File

@Composable
fun StepAnimalInfo(
    uiState: CreateRideUiState,
    onUpdateInfo: (String, AnimalSpecies, String) -> Unit,
    onPhotoSelected: (Uri) -> Unit,
    onNext: () -> Unit
) {
    val context = LocalContext.current
    // Guardar la URI como String en rememberSaveable para que sobreviva a la recreación de la Activity
    var tempCameraUriString by rememberSaveable { mutableStateOf<String?>(null) }

    // Launcher para la cámara del sistema
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && !tempCameraUriString.isNullOrEmpty()) {
            val savedUri = Uri.parse(tempCameraUriString)
            onPhotoSelected(savedUri)
        }
    }

    // Launcher para la galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onPhotoSelected(it) }
    }

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
                onClick = {
                    val uri = createImageUri(context)
                    tempCameraUriString = uri.toString()
                    cameraLauncher.launch(uri)
                },
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

// Función auxiliar para crear un archivo temporal y obtener su URI seguro
private fun createImageUri(context: Context): Uri {
    val file = File.createTempFile(
        "animal_${System.currentTimeMillis()}",
        ".jpg",
        context.cacheDir
    )
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )
}