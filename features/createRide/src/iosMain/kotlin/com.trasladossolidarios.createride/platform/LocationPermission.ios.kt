package com.trasladossolidarios.createride.platform

import androidx.compose.runtime.Composable

// Pendiente iOS: el permiso de ubicación se implementará cuando exista app iOS.
@Composable
actual fun rememberLocationPermissionRequest(onGranted: () -> Unit): () -> Unit {
    return {}
}
