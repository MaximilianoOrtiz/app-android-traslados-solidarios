package com.trasladossolidarios.createride.platform

import androidx.compose.runtime.Composable

// Pendiente iOS: la selección de imagen (cámara/galería) se implementará cuando exista app iOS.
@Composable
actual fun rememberImagePicker(onImagePicked: (String) -> Unit): ImagePickerActions =
    ImagePickerActions(
        launchCamera = {},
        launchGallery = {}
    )
