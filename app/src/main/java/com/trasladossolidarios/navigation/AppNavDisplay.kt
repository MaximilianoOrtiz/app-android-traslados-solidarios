package com.trasladossolidarios.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay

import com.trasladossolidarios.di.AppContainer
import com.trasladossolidarios.home.DisponiblesScreen.DisponibleScreen
import com.trasladossolidarios.home.HomeRoute
import com.trasladossolidarios.home.InicioScreen.InicioScreen
import com.trasladossolidarios.home.MisTrasladosScreen.MisTrasladosScreen
import com.trasladossolidarios.home.ProfileViewModel

/**
 * La navegación vive en :app. Las features no se conocen entre sí:
 * solo exponen callbacks (onTodoClick, onBack…) y acá decidimos qué hace cada uno.
 */
@Composable
fun AppNavDisplay(
    navigationState: NavigationState,
    container: AppContainer,
    modifier: Modifier = Modifier
) {
    // entryProvider: "para esta key, mostrá esta pantalla". La key llega ya tipada:
    val entryProvider = entryProvider<NavKey> {

        entry<InicioDestination> { InicioScreen() }

        entry<DisponiblesDestination> { DisponibleScreen() }

        entry<MisTrasladosDestination> { MisTrasladosScreen() }

        entry<ProfileDestination> {
            val viewModel = viewModel {
                ProfileViewModel(
                    logoutUser = container.logoutUser,
                )
            }
            HomeRoute(
                viewModel
            )
        }
    }

    NavDisplay(
        entries = navigationState.toEntries(entryProvider),
        onBack = navigationState::goBack,
        modifier = modifier
    )
}

/**
 * Convierte cada back stack en entradas "decoradas" y junta las de los tabs visibles.
 *
 * Decoramos TODOS los back stacks, aunque no se vean: así, al cambiar de tab,
 * las pantallas del otro tab conservan su ViewModel y su estado (scroll, formularios…).
 */
@Composable
private fun NavigationState.toEntries(
    entryProvider: (NavKey) -> NavEntry<NavKey>
): List<NavEntry<NavKey>> {
    val entriesByTab = backStacks.mapValues { (_, backStack) ->
        rememberDecoratedNavEntries(
            backStack = backStack,
            entryDecorators = listOf(
                // Guarda el estado de rememberSaveable de cada pantalla
                rememberSaveableStateHolderNavEntryDecorator(),
                // Le da a cada pantalla su propio ViewModelStore
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider
        )
    }
    return visibleTabs.flatMap { tab -> entriesByTab.getValue(tab) }
}
