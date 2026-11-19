package com.trasladossolidarios.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey

/**
 * Envuelve a NavDisplay (androidx.navigation3:navigation3-ui).
 * El artefacto no publica variantes iOS, así que el commonMain declara el expect
 * y cada plataforma aporta su actual (iOS queda pendiente, como el resto del SO).
 */
@Composable
expect fun NavDisplayHost(
    entries: List<NavEntry<NavKey>>,
    onBack: () -> Unit,
    modifier: Modifier,
)
