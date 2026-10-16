package com.trasladossolidarios.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
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
    PROFILE(
        startKey = ProfileDestination,
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        label = R.string.tab_profile
    )
}