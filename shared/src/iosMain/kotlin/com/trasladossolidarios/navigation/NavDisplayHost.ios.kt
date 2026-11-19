package com.trasladossolidarios.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey

@Composable
actual fun NavDisplayHost(
    entries: List<NavEntry<NavKey>>,
    onBack: () -> Unit,
    modifier: Modifier,
) {
    // Pendiente iOS: navigation3-ui no publica variantes iOS.
    Box(modifier = modifier)
}
