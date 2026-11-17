package com.trasladossolidarios.createride.platform

import androidx.compose.runtime.Composable

class ImagePickerActions(
    val launchCamera: () -> Unit,
    val launchGallery: () -> Unit
)

@Composable
expect fun rememberImagePicker(onImagePicked: (String) -> Unit): ImagePickerActions
