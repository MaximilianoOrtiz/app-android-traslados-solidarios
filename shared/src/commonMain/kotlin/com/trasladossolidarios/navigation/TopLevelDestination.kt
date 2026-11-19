package com.trasladossolidarios.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import apmobiletp2.shared.generated.resources.Res
import org.jetbrains.compose.resources.StringResource
import apmobiletp2.shared.generated.resources.tab_disponibles
import apmobiletp2.shared.generated.resources.tab_inicio
import apmobiletp2.shared.generated.resources.tab_mis_traslados
import apmobiletp2.shared.generated.resources.tab_perfil

/**
 * Los tabs de la bottom bar. Cada uno tiene su propio back stack, que arranca en [key].
 * Agregar un tab nuevo = agregar una entrada acá y sus pantallas en AppNavDisplay.
 */
enum class TopLevelDestination(
    val key: NavKey,
    val label: StringResource,
    val icon: ImageVector,
    val selectIcon: ImageVector
) {
    HOME(HomeDestination, Res.string.tab_inicio, Icons.Outlined.Home, Icons.Filled.Home),
    OPENRIDES(OpenRidesDestination, Res.string.tab_disponibles, Icons.Outlined.Search, Icons.Filled.Search),
    MYRIDES(MyRidesDestination, Res.string.tab_mis_traslados, Icons.Outlined.DateRange, Icons.Filled.DateRange),
    PROFILE(ProfileDestination, Res.string.tab_perfil, Icons.Outlined.Person, Icons.Filled.Person)
}
