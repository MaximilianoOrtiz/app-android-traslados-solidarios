package com.trasladossolidarios.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.trasladossolidarios.domain.enum.AnimalSpecies
import com.trasladossolidarios.domain.enum.RideStatus
import com.trasladossolidarios.domain.model.Animal
import com.trasladossolidarios.domain.model.Location
import com.trasladossolidarios.domain.model.Ride
import com.trasladossolidarios.ui.theme.ApMobileTP2Theme
import com.trasladossolidarios.ui.theme.AppTheme
import com.ui.R
import java.time.Instant

/**
 * Pantalla de detalle del traslado, autocontenida:
 * foto con botón de volver y chip de estado superpuestos, nombre y especie
 * del animal, cards "Sobre {nombre}" e "Información del traslado",
 * divider y publicante.
 */
@Composable
fun RideDetailContent(
    ride: Ride,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    val animalName = ride.animal.name.ifBlank {
        stringResource(R.string.ride_card_animal_name_fallback)
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
                    .aspectRatio(3f / 2f)
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
                    contentDescription = stringResource(R.string.core_ui_back),
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

            AboutAnimalCard(
                animal = ride.animal,
                notes = ride.notes
            )

            Spacer(modifier = Modifier.height(AppTheme.spacing.md))

            TransferInfoCard(
                origin = ride.origin,
                destination = ride.destination,
                dateOfTransfer = ride.dateOfTransfer
            )

            Spacer(modifier = Modifier.height(AppTheme.spacing.md))

            HorizontalDivider(color = AppTheme.colors.outlineVariant)

            Spacer(modifier = Modifier.height(AppTheme.spacing.md))

            PublisherRow(name = stringResource(R.string.ride_detail_publisher_placeholder))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RideDetailContentPreview() {
    ApMobileTP2Theme {
        RideDetailContent(
            ride = Ride(
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
            ),
            onBack = {}
        )
    }
}
