package com.trasladossolidarios.createride.platform

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File

@Composable
actual fun rememberImagePicker(onImagePicked: (String) -> Unit): ImagePickerActions {
    val context = LocalContext.current
    // Guardar la URI como String en rememberSaveable para que sobreviva a la recreación de la Activity
    var tempCameraUriString by rememberSaveable { mutableStateOf<String?>(null) }

    // Launcher para la cámara del sistema
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && !tempCameraUriString.isNullOrEmpty()) {
            onImagePicked(Uri.parse(tempCameraUriString).toString())
        }
    }

    // Launcher para la galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onImagePicked(it.toString()) }
    }

    return ImagePickerActions(
        launchCamera = {
            val uri = createImageUri(context)
            tempCameraUriString = uri.toString()
            cameraLauncher.launch(uri)
        },
        launchGallery = {
            galleryLauncher.launch("image/*")
        }
    )
}

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
