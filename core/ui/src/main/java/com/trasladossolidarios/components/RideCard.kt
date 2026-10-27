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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ui.R
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
            containerColor = AppTheme.colors.background
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {

        Row(modifier = Modifier.padding(AppTheme.spacing.xs)) {
            AnimalPhoto(
                animal = ride.animal,
                modifier = Modifier
                    .width(100.dp)
                    .height(130.dp)
            )
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
                        text = ride.animal.name.ifBlank { stringResource(R.string.ride_card_animal_name_fallback) },
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
                        text = stringResource(R.string.ride_card_view_action),
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
            )
            .clip(
                RoundedCornerShape(
                    topStart = 16.dp,
                    bottomStart = 0.dp,
                    topEnd = 0.dp,
                    bottomEnd = 0.dp
                )
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

        if (!animal.photoUrl.isNullOrBlank()) {
            AsyncImage(
                model = animal.photoUrl,
                contentDescription = animal.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
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

@Composable
fun RideStatus.label(): String = stringResource(
    when (this) {
        RideStatus.OPEN -> R.string.ride_card_status_open
        RideStatus.ASSIGNED -> R.string.ride_card_status_assigned
        RideStatus.COMPLETED -> R.string.ride_card_status_completed
        RideStatus.CANCELLED -> R.string.ride_card_status_cancelled
        RideStatus.UNKNOWN -> R.string.ride_card_status_unknown
    }
)

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

@Composable
fun formatTransferDateTime(
    instant: Instant,
    today: LocalDate = LocalDate.now(),
    zoneId: ZoneId = ZoneId.systemDefault()
): String {
    val todayLabel = stringResource(R.string.ride_card_date_today)
    val tomorrowLabel = stringResource(R.string.ride_card_date_tomorrow)
    val dateTime = instant.atZone(zoneId)
    val date = dateTime.toLocalDate()
    val dayLabel = when (date) {
        today -> todayLabel
        today.plusDays(1) -> tomorrowLabel
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
                        photoUrl = "https://img.magnific.com/foto-gratis/nina-juega-conejo-domestico-calle-concepto-mascota_169016-63700.jpg?semt=ais_hybrid&w=740&q=80"
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
