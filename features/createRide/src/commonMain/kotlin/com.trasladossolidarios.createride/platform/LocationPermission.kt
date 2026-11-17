package com.trasladossolidarios.createride.platform

import androidx.compose.runtime.Composable

@Composable
expect fun rememberLocationPermissionRequest(onGranted: () -> Unit): () -> Unit
