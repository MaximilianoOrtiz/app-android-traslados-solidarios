package com.trasladossolidarios.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File
//Componente de botón que genera el archivo temporal con FileProvider y abre la cámara
@Composable
fun CameraPicker(
    onImageCaptured: (Uri) -> Unit
) {
    val context = LocalContext.current
    var tempUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempUri != null) {
            onImageCaptured(tempUri!!)
        }
    }

    Button(onClick = {
        val file = File.createTempFile("pet_", ".jpg", context.cacheDir)
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
        tempUri = uri
        cameraLauncher.launch(uri)
    }) {
        Text("Tomar foto de la mascota") //fix
    }
}