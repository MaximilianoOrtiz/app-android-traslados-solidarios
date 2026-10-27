package com.trasladossolidarios.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.LocationOn

import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material.icons.outlined.TripOrigin
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trasladossolidarios.domain.enum.AnimalSpecies
import com.trasladossolidarios.domain.enum.RideStatus
import com.trasladossolidarios.domain.model.Animal
import com.trasladossolidarios.domain.model.Location
import com.trasladossolidarios.domain.model.Ride
import com.trasladossolidarios.ui.theme.ApMobileTP2Theme
import com.trasladossolidarios.ui.theme.AppTheme
import com.trasladossolidarios.ui.theme.Neutral96
import com.trasladossolidarios.ui.theme.Outline
import com.trasladossolidarios.ui.theme.Pending
import com.trasladossolidarios.ui.theme.PendingContainer
import com.trasladossolidarios.ui.theme.Success
import com.trasladossolidarios.ui.theme.SuccessContainer
import com.trasladossolidarios.ui.theme.Urgent
import com.trasladossolidarios.ui.theme.UrgentContainer
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun RideCard(
    ride: Ride,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = AppTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.background   // o el color que quieras
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(AppTheme.spacing.md)) {
            AnimalPhoto(animal = ride.animal)

            Spacer(modifier = Modifier.width(AppTheme.spacing.md))

            Column(verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.sm)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Pets,
                        contentDescription = null,
                        tint = AppTheme.colors.tertiary,
                        modifier = Modifier.size(AppTheme.typography.titleMedium.fontSize.value.dp)
                    )

                    Spacer(modifier = Modifier.width(AppTheme.spacing.xs))

                    Text(
                        text = ride.animal.name.ifBlank { "Animal sin nombre" },
                        style = AppTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                LocationLine(
                    icon = Icons.Outlined.TripOrigin,
                    address = ride.origin.address,
                    tint = AppTheme.colors.primary
                )

                LocationLine(
                    icon = Icons.Outlined.LocationOn,
                    address = ride.destination.address,
                    tint = AppTheme.colors.secondary
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarToday,
                        contentDescription = null,
                        tint = AppTheme.colors.onSurfaceVariant,
                        modifier = Modifier.size(AppTheme.typography.labelMedium.fontSize.value.dp)
                    )

                    Spacer(modifier = Modifier.width(AppTheme.spacing.xs))

                    Text(
                        text = formatTransferDateTime(ride.dateOfTransfer),
                        style = AppTheme.typography.bodyMedium,
                        color = AppTheme.colors.onSurfaceVariant
                    )
                }

                RideStatusChip(status = ride.status)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ver traslado",
                        style = AppTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.colors.primary
                    )

                    Icon(
                        imageVector = Icons.Outlined.ChevronRight,
                        contentDescription = null,
                        tint = AppTheme.colors.primary
                    )
                }
            }
        }
    }
}

@Composable
private fun AnimalPhoto(animal: Animal, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(96.dp)
            .background(
                color = AppTheme.colors.primaryContainer,
                shape = AppTheme.shapes.medium
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = when (animal.species) {
                AnimalSpecies.DOG, AnimalSpecies.CAT -> Icons.Outlined.Pets
                AnimalSpecies.OTHER, AnimalSpecies.UNKNOWN -> Icons.Outlined.QuestionMark
            },
            contentDescription = null,
            tint = AppTheme.colors.onPrimaryContainer,
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
private fun LocationLine(
    icon: ImageVector,
    address: String,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(AppTheme.typography.bodyMedium.fontSize.value.dp)
        )

        Spacer(modifier = Modifier.width(AppTheme.spacing.xs))

        Text(
            text = address,
            style = AppTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun RideStatusChip(
    status: RideStatus,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = status.containerColor()
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = AppTheme.spacing.sm,
                vertical = AppTheme.spacing.xs
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(color = status.contentColor(), shape = CircleShape)
            )

            Spacer(modifier = Modifier.width(AppTheme.spacing.xs))

            Text(
                text = status.label(),
                style = AppTheme.typography.labelMedium,
                color = status.contentColor()
            )
        }
    }
}

fun RideStatus.label(): String = when (this) {
    RideStatus.OPEN -> "Buscando voluntario"
    RideStatus.ASSIGNED -> "Voluntario asignado"
    RideStatus.COMPLETED -> "Completado"
    RideStatus.CANCELLED -> "Cancelado"
    RideStatus.UNKNOWN -> "Sin estado"
}

@Composable
fun RideStatus.containerColor(): Color = when (this) {
    RideStatus.OPEN -> Pending
    RideStatus.ASSIGNED, RideStatus.COMPLETED -> Success
    RideStatus.CANCELLED -> Urgent
    RideStatus.UNKNOWN -> Neutral96
}

@Composable
fun RideStatus.contentColor(): Color = when (this) {
    RideStatus.OPEN -> PendingContainer
    RideStatus.ASSIGNED, RideStatus.COMPLETED -> SuccessContainer
    RideStatus.CANCELLED -> UrgentContainer
    RideStatus.UNKNOWN -> Outline
}

fun formatTransferDateTime(
    instant: Instant,
    today: LocalDate = LocalDate.now(),
    zoneId: ZoneId = ZoneId.systemDefault()
): String {
    val dateTime = instant.atZone(zoneId)
    val date = dateTime.toLocalDate()
    val dayLabel = when (date) {
        today -> "Hoy"
        today.plusDays(1) -> "Mañana"
        else -> date.format(DateTimeFormatter.ofPattern("d MMM", Locale.getDefault()))
    }
    val time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()))
    return "$dayLabel · $time"
}

@Preview(showBackground = true)
@Composable
private fun RideCardPreview() {
    ApMobileTP2Theme {
        Column(verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.md)) {
            RideCard(
                ride = Ride(
                    id = "1",
                    ownerId = "owner",
                    status = RideStatus.OPEN,
                    animal = Animal(
                        name = "Luna",
                        species = AnimalSpecies.CAT,
                        description = "Gris, tranquila",
                        photoUrl = null
                    ),
                    origin = Location(latitude = null, longitude = null, address = "Villa del Parque, CABA"),
                    destination = Location(latitude = null, longitude = null, address = "Clínica Veterinaria Mascotas"),
                    notes = "",
                    acceptedApplicantId = null,
                    dateOfTransfer = Instant.now().plusSeconds(86_400)
                ),
                onClick = {}
            )

            RideCard(
                ride = Ride(
                    id = "2",
                    ownerId = "owner",
                    status = RideStatus.ASSIGNED,
                    animal = Animal(
                        name = "Toby",
                        species = AnimalSpecies.DOG,
                        description = "",
                        photoUrl = null
                    ),
                    origin = Location(latitude = null, longitude = null, address = "Almagro, CABA"),
                    destination = Location(latitude = null, longitude = null, address = "Refugio San Roque"),
                    notes = "",
                    acceptedApplicantId = "volunteer-1",
                    dateOfTransfer = Instant.now()
                ),
                onClick = {}
            )
        }
    }
}
