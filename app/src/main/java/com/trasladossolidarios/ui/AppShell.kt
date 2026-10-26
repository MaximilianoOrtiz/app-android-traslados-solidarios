package com.trasladossolidarios.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.trasladossolidarios.navigation.AppNavDisplay
import com.trasladossolidarios.navigation.NavigationState
import com.trasladossolidarios.navigation.TopLevelDestination
import com.trasladossolidarios.navigation.rememberNavigationState
import com.trasladossolidarios.ui.theme.AppTheme


/** Estructura general de la app: bottom bar + NavDisplay. Cada pantalla tiene su propio Scaffold adentro. */
@Composable
fun AppShell(
    currentUserId: String,
    navigationState: NavigationState = rememberNavigationState()
) {
    Scaffold(
        // Los insets (status bar, barra de gestos) los maneja el Scaffold de cada pantalla.
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            // Leer navigationState acá es leer estado: cuando cambia el back stack, esto se recompone solo.
            // La bottom bar solo se muestra en la pantalla principal de cada tab, no en los detalles.
            AnimatedVisibility(
                visible = navigationState.isOnTabRoot,
                enter = slideInVertically { it },
                exit = slideOutVertically { it }
            ) {
                AppBottomBar(
                    selected = navigationState.currentTab,
                    onSelect = navigationState::selectTab
                )
            }
        }
    ) { innerPadding ->
        AppNavDisplay(
            navigationState = navigationState,
            // consumeWindowInsets: la bottom bar ya ocupa el espacio de la barra de navegación,
            // así las pantallas no vuelven a sumarlo.
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
            currentUserId
        )
    }
}

@Composable
fun AppBottomBar(selected: TopLevelDestination, onSelect: (TopLevelDestination) -> Unit) {
    val colors = AppTheme.colors
    NavigationBar (

    ){
        TopLevelDestination.entries.forEach { tab ->
            val isSelected = tab == selected
            NavigationBarItem(
                selected = isSelected,
                onClick = { onSelect(tab) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) tab.selectIcon else tab.icon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(tab.labelRes))},

            )
        }
    }
}
