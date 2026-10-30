package com.trasladossolidarios.openrides.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trasladossolidarios.components.AnimalPhotoHeader
import com.trasladossolidarios.components.PrimaryButton
import com.trasladossolidarios.components.RideStatusChip
import com.trasladossolidarios.components.formatTransferDateTime
import com.trasladossolidarios.components.toMessage
import com.trasladossolidarios.domain.enum.AnimalSpecies
import com.trasladossolidarios.domain.enum.RideStatus
import com.trasladossolidarios.domain.model.Animal
import com.trasladossolidarios.domain.model.Location
import com.trasladossolidarios.domain.model.Ride
import com.trasladossolidarios.openrides.R
import com.trasladossolidarios.ui.theme.ApMobileTP2Theme
import com.trasladossolidarios.ui.theme.AppTheme
import com.trasladossolidarios.ui.theme.Pending
import com.trasladossolidarios.ui.theme.PendingContainer
import components.ErrorContent
import components.LoadingContent
import java.time.Instant


@Composable
fun RideDetailRoute(
    viewModel: RideDetailViewModel,
    onBack: () -> Unit,
    onAccept: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    RideDetailScreen(
        uiState = uiState,
        onBack = onBack,
        onRetry = viewModel::onRetry,
        onAccept = onAccept
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideDetailScreen(
    uiState: RideDetailState,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    onAccept: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,

        bottomBar = {
            if (uiState is RideDetailState.Success) {
                Surface(
                    color = AppTheme.colors.background,
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .padding(AppTheme.spacing.md)
                    ) {
                        PrimaryButton(
                            text = stringResource(R.string.ride_detail_accept_action),
                            onClick = onAccept,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        when (uiState) {
            is RideDetailState.Loading -> LoadingContent(
                message = stringResource(R.string.ride_detail_loading),
                modifier = Modifier.padding(paddingValues)
            )

            is RideDetailState.Error -> ErrorContent(
                title = stringResource(R.string.ride_detail_error_title),
                message = uiState.error.toMessage(),
                onRetry = onRetry,
                modifier = Modifier.padding(paddingValues)
            )

            is RideDetailState.Success -> RideDetailContent(
                ride = uiState.ride,
                onBack = onBack,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
private fun RideDetailContent(
    ride: Ride,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    val animalName = ride.animal.name.ifBlank {
        stringResource(R.string.ride_detail_animal_name_fallback)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box {
            AnimalPhotoHeader(
                animal = ride.animal,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f/2f)
            )
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(
                        start = AppTheme.spacing.md,
                        top = AppTheme.spacing.md
                    )
                    .background(
                        color = AppTheme.colors.background.copy(alpha = 0.40f),
                        shape = CircleShape
                    )


            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.books_back),
                    tint = Color.White
                )
            }

            RideStatusChip(
                status = ride.status,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(AppTheme.spacing.md)
                    .offset(y = AppTheme.spacing.sm)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = AppTheme.spacing.md,
                    vertical = AppTheme.spacing.lg
                )
        ) {
            Text(
                text = animalName,
                style = AppTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(AppTheme.spacing.sm))

            AnimalSpeciesChip(species = ride.animal.species)

            Spacer(modifier = Modifier.height(AppTheme.spacing.md))

            DetailSectionCard(
                title = stringResource(R.string.ride_detail_about_title, animalName)
            ) {
                Text(
                    text = ride.animal.description,
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colors.onSurface
                )
                if (ride.notes.isNotBlank()) {
                    Spacer(modifier = Modifier.height(AppTheme.spacing.md))
                    WarningBanner(text = ride.notes)
                }
            }

            Spacer(modifier = Modifier.height(AppTheme.spacing.md))

            DetailSectionCard(
                title = stringResource(R.string.ride_detail_transfer_info_title)
            ) {
                TransferTimeline(
                    origin = ride.origin,
                    destination = ride.destination
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.md))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarToday,
                        contentDescription = null,
                        tint = AppTheme.colors.onSurfaceVariant,
                        modifier = Modifier.size(AppTheme.typography.bodyMedium.fontSize.value.dp)
                    )

                    Spacer(modifier = Modifier.width(AppTheme.spacing.xs))

                    Text(
                        text = formatTransferDateTime(ride.dateOfTransfer),
                        style = AppTheme.typography.bodyMedium,
                        color = AppTheme.colors.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(AppTheme.spacing.md))

            HorizontalDivider(color = AppTheme.colors.outlineVariant)

            Spacer(modifier = Modifier.height(AppTheme.spacing.md))

            PublisherRow(name = stringResource(R.string.ride_detail_publisher_placeholder))
        }
    }
}

@Composable
private fun DetailSectionCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = AppTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.surface
        ),
        border = BorderStroke(1.dp, AppTheme.colors.outlineVariant)
    ) {
        Column(
            modifier = Modifier.padding(AppTheme.spacing.md),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.sm)
        ) {
            Text(
                text = title,
                style = AppTheme.typography.titleMedium,
                color = AppTheme.colors.onSurface
            )
            content()
        }
    }
}

@Composable
private fun WarningBanner(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = AppTheme.shapes.medium,
        color = PendingContainer
    ) {
        Row(
            modifier = Modifier.padding(AppTheme.spacing.md),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.sm),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Outlined.Warning,
                contentDescription = null,
                tint = Pending,
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = text,
                style = AppTheme.typography.bodyMedium,
                color = AppTheme.colors.onSurface,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun TransferTimeline(
    origin: Location,
    destination: Location,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        TimelineEntry(
            label = stringResource(R.string.ride_detail_origin),
            address = origin.address,
            isLast = false
        )
        TimelineEntry(
            label = stringResource(R.string.ride_detail_destination),
            address = destination.address,
            isLast = true
        )
    }
}

@Composable
private fun TimelineEntry(
    label: String,
    address: String,
    isLast: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier
                .width(16.dp)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLast) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .background(AppTheme.colors.primary, CircleShape)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .border(2.dp, AppTheme.colors.primary, CircleShape)
                )
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .weight(1f)
                        .background(AppTheme.colors.outlineVariant)
                )
            }
        }

        Spacer(modifier = Modifier.width(AppTheme.spacing.sm))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = AppTheme.spacing.sm)
        ) {
            Text(
                text = label.uppercase(),
                style = AppTheme.typography.labelSmall,
                color = AppTheme.colors.onSurfaceVariant
            )
            Text(
                text = address,
                style = AppTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = AppTheme.colors.onSurface
            )
        }
    }
}

@Composable
private fun AnimalSpeciesChip(
    species: AnimalSpecies,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = AppTheme.colors.surfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = AppTheme.spacing.sm,
                vertical = AppTheme.spacing.xs
            ),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.xs),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = species.icon(),
                contentDescription = null,
                tint = AppTheme.colors.tertiary,
                modifier = Modifier.size(AppTheme.typography.labelMedium.fontSize.value.dp)
            )
            Text(
                text = species.label(),
                style = AppTheme.typography.labelMedium,
                color = AppTheme.colors.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun AnimalSpecies.icon(): ImageVector = when (this) {
    AnimalSpecies.DOG, AnimalSpecies.CAT -> Icons.Outlined.Pets
    AnimalSpecies.OTHER, AnimalSpecies.UNKNOWN -> Icons.Outlined.QuestionMark
}

@Composable
private fun AnimalSpecies.label(): String = stringResource(
    when (this) {
        AnimalSpecies.DOG -> R.string.animal_species_dog
        AnimalSpecies.CAT -> R.string.animal_species_cat
        AnimalSpecies.OTHER -> R.string.animal_species_other
        AnimalSpecies.UNKNOWN -> R.string.animal_species_unknown
    }
)

@Composable
private fun PublisherRow(
    name: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(AppTheme.colors.primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.firstOrNull()?.uppercase() ?: "",
                style = AppTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = AppTheme.colors.onPrimary
            )
        }

        Text(
            text = stringResource(R.string.ride_detail_publisher, name),
            style = AppTheme.typography.bodyMedium,
            color = AppTheme.colors.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RideDetailScreenPreview() {
    ApMobileTP2Theme {
        RideDetailScreen(
            uiState = RideDetailState.Success(
                Ride(
                    id = "1",
                    ownerId = "owner",
                    status = RideStatus.OPEN,
                    animal = Animal(
                        name = "Luna",
                        species = AnimalSpecies.CAT,
                        description = "Gatita gris tabulada, muy asustada. Fue encontrada bajo un auto en el barrio",
                        photoUrl = null
                    ),
                    origin = Location(
                        latitude = null,
                        longitude = null,
                        address = "Villa del Parque, CABA"
                    ),
                    destination = Location(
                        latitude = null,
                        longitude = null,
                        address = "Clínica Veterinaria Mascotas - Veterinaria - Palermo"
                    ),
                    notes = "Necesita transportadora obligatoriamente. Está asustada, requiere manejo suave.",
                    acceptedApplicantId = null,
                    dateOfTransfer = Instant.now().plusSeconds(86_400)
                )
            ),
            onBack = {},
            onRetry = {}
        )
    }
}
