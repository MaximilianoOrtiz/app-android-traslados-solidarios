package com.trasladossolidarios.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.trasladossolidarios.home.HomeRouter
import com.trasladossolidarios.home.HomeViewModel
import com.trasladossolidarios.myrides.MyRidesRouter
import com.trasladossolidarios.myrides.MyRidesViewModel
import com.trasladossolidarios.openrides.detail.RideDetailRoute
import com.trasladossolidarios.openrides.detail.RideDetailViewModel
import com.trasladossolidarios.openrides.list.OpenRidesRouter
import com.trasladossolidarios.openrides.list.OpenRidesViewModel

import com.trasladossolidarios.profile.ProfileRouter
import com.trasladossolidarios.profile.ProfileViewModel
import kotlin.String

/**
 * La navegación vive en :app. Las features no se conocen entre sí:
 * solo exponen callbacks (onTodoClick, onBack…) y acá decidimos qué hace cada uno.
 */
@Composable
fun AppNavDisplay(
    navigationState: NavigationState,
    modifier: Modifier = Modifier,
    currentUserId: String,
) {
    // entryProvider: "para esta key, mostrá esta pantalla". La key llega ya tipada:
    val entryProvider = entryProvider<NavKey> {

        entry<ProfileDestination> {
            val profileViewModel: ProfileViewModel = hiltViewModel()
            ProfileRouter(
                profileViewModel
            )
        }

        entry<HomeDestination> {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeRouter(
                homeViewModel
            )
        }

        entry<MyRidesDestination> {
            val myRidesViewModel: MyRidesViewModel = hiltViewModel()
            MyRidesRouter(
                myRidesViewModel
            )
        }

        entry<OpenRidesDestination> {
            val openRidesViewModel: OpenRidesViewModel = hiltViewModel()
            OpenRidesRouter(
                currentUserId,
                viewModel = openRidesViewModel,
                onRideClick = { rideId -> navigationState.navigate(RideDetailDestination(rideId)) }
            )
        }
        entry<RideDetailDestination> { key ->
            // El detalle necesita un dato que Hilt NO puede conocer: el id que eligió el
            // usuario. Se lo pasamos con la @AssistedFactory del ViewModel; el resto de
            // las dependencias las sigue poniendo el grafo.
            //
            // No hace falta un `key` string para distinguir instancias: el
            // rememberViewModelStoreNavEntryDecorator de abajo le da a cada entrada del
            // back stack su propio ViewModelStore (usa NavEntry.contentKey).
            val viewModel = hiltViewModel<RideDetailViewModel, RideDetailViewModel.Factory>(
                creationCallback = { factory -> factory.create(key.rideId) }
            )
            RideDetailRoute (viewModel = viewModel, onBack = { navigationState.goBackFrom(key) })
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
