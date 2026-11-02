package com.trasladossolidarios.myrides

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trasladossolidarios.components.RideCard
import com.trasladossolidarios.components.toMessage
import com.trasladossolidarios.domain.model.Ride
import com.trasladossolidarios.ui.theme.AppTheme
import com.trasladossolidarios.ui.theme.AppTheme.spacing
import components.ErrorContent
import components.LoadingContent


@Composable
fun MyRidesRouter(
    currentUserId: String,
    viewModel: MyRidesViewModel,
    onRideClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(currentUserId) {
        viewModel.observeRides(currentUserId)
    }

    MyRidesScreen(
        uiState = uiState,
        onTabSelected = viewModel::onTabSelected,
        onRideClick = onRideClick,
        onRetry = { viewModel.onRetry(currentUserId) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRidesScreen(
    uiState: MyRidesState,
    modifier: Modifier = Modifier,
    onTabSelected: (MyRidesTab) -> Unit,
    onRideClick: (String) -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = stringResource(R.string.my_rides_title),
                            style = AppTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(spacing.xs))
                        Text(
                            text = stringResource(R.string.my_rides_subtitle),
                            style = AppTheme.typography.bodyMedium
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppTheme.colors.primary,
                    titleContentColor = AppTheme.colors.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            MyRidesTabs(
                selectedTab = uiState.selectedTab,
                publishedCount = uiState.publishedCount,
                onTabSelected = onTabSelected
            )

            when (uiState.selectedTab) {
                MyRidesTab.PUBLISHED -> PublishedRidesContent(
                    state = uiState.published,
                    onRideClick = onRideClick,
                    onRetry = onRetry
                )

                MyRidesTab.AS_VOLUNTEER -> VolunteerRidesPlaceholder()
            }
        }
    }
}

@Composable
private fun MyRidesTabs(
    selectedTab: MyRidesTab,
    publishedCount: Int?,
    onTabSelected: (MyRidesTab) -> Unit
) {
    TabRow(
        selectedTabIndex = selectedTab.ordinal,
        containerColor = AppTheme.colors.surface,
        contentColor = AppTheme.colors.primary
    ) {
        MyRidesTab.entries.forEach { tab ->
            Tab(
                selected = tab == selectedTab,
                onClick = { onTabSelected(tab) },
                selectedContentColor = AppTheme.colors.primary,
                unselectedContentColor = AppTheme.colors.onSurfaceVariant,
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(tab.labelRes()),
                            style = AppTheme.typography.titleSmall
                        )
                        if (tab == MyRidesTab.PUBLISHED && publishedCount != null) {
                            Spacer(Modifier.width(spacing.sm))
                            CountBadge(count = publishedCount)
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun CountBadge(count: Int) {
    Surface(
        shape = CircleShape,
        color = AppTheme.colors.primaryContainer
    ) {
        Text(
            text = count.toString(),
            style = AppTheme.typography.labelSmall,
            color = AppTheme.colors.onPrimaryContainer,
            modifier = Modifier.padding(horizontal = spacing.sm, vertical = spacing.xs)
        )
    }
}

@StringRes
private fun MyRidesTab.labelRes(): Int = when (this) {
    MyRidesTab.PUBLISHED -> R.string.my_rides_tab_published
    MyRidesTab.AS_VOLUNTEER -> R.string.my_rides_tab_as_volunteer
}

@Composable
private fun PublishedRidesContent(
    state: PublishedRidesState,
    onRideClick: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (state) {
        PublishedRidesState.Loading -> Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingContent(stringResource(R.string.my_rides_loading))
        }

        is PublishedRidesState.Error -> ErrorContent(
            title = stringResource(R.string.my_rides_error_title),
            message = state.error.toMessage(),
            onRetry = onRetry,
            modifier = modifier
                .fillMaxSize()
                .padding(spacing.lg)
        )

        is PublishedRidesState.Success -> PublishedRidesList(
            rides = state.rides,
            onRideClick = onRideClick,
            modifier = modifier
        )
    }
}

@Composable
private fun PublishedRidesList(
    rides: List<Ride>,
    onRideClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (rides.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(spacing.lg),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.my_rides_published_empty),
                style = AppTheme.typography.bodyLarge,
                color = AppTheme.colors.onSurfaceVariant
            )
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(spacing.md),
            verticalArrangement = Arrangement.spacedBy(spacing.sm + spacing.xs)
        ) {
            items(items = rides, key = { ride -> ride.id }) { ride ->
                RideCard(
                    ride = ride,
                    onClick = { onRideClick(ride.id) }
                )
            }
        }
    }
}

@Composable
private fun VolunteerRidesPlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(spacing.lg),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.my_rides_coming_soon),
            style = AppTheme.typography.bodyLarge,
            color = AppTheme.colors.onSurfaceVariant
        )
    }
}