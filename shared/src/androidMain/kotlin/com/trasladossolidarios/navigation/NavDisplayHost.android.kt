package com.trasladossolidarios.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay

@Composable
actual fun NavDisplayHost(
    entries: List<NavEntry<NavKey>>,
    onBack: () -> Unit,
    modifier: Modifier,
) {
    NavDisplay(
        entries = entries,
        onBack = onBack,
        modifier = modifier,
    )
}
