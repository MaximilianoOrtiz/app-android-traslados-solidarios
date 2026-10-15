package com.trasladossolidarios.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.trasladossolidarios.R

/**
 * Los tabs de la bottom bar. Cada uno tiene su propio back stack, que arranca en [startKey].
 * Agregar un tab nuevo = agregar una entrada acá y sus pantallas en AppNavDisplay.
 */
enum class TopLevelDestination(
    val startKey: NavKey,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val label: Int
) {
    HOME(
        startKey = AuthDestination,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        label = R.string.tab_home
    )
}