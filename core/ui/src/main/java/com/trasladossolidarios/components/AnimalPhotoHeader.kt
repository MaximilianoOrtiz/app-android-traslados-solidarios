package com.trasladossolidarios.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.trasladossolidarios.domain.enum.AnimalSpecies
import com.trasladossolidarios.domain.model.Animal
import com.trasladossolidarios.ui.theme.ApMobileTP2Theme
import com.trasladossolidarios.ui.theme.AppTheme

/**
 * Foto del animal a ancho completo para la cabecera del detalle.
 * Si no hay foto, muestra un placeholder con el ícono de la especie.
 */
@Composable
fun AnimalPhotoHeader(
    animal: Animal,
    modifier: Modifier = Modifier
) {
    val photoShape = RoundedCornerShape(
        bottomStart = AppTheme.spacing.md,
        bottomEnd = AppTheme.spacing.md
    )

    Box(
        modifier = modifier
            .background(
                color = AppTheme.colors.primaryContainer,
                shape = photoShape
            )
            .clip(photoShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = when (animal.species) {
                AnimalSpecies.DOG, AnimalSpecies.CAT -> Icons.Outlined.Pets
                AnimalSpecies.OTHER, AnimalSpecies.UNKNOWN -> Icons.Outlined.QuestionMark
            },
            contentDescription = null,
            tint = AppTheme.colors.onPrimaryContainer,
            modifier = Modifier.size(64.dp)
        )

        if (!animal.photoUrl.isNullOrBlank()) {
            AsyncImage(
                model = animal.photoUrl,
                contentDescription = animal.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AnimalPhotoHeaderPreview() {
    ApMobileTP2Theme {
        AnimalPhotoHeader(
            animal = Animal(
                name = "Luna",
                species = AnimalSpecies.CAT,
                description = "",
                photoUrl = null
            ),
            modifier = Modifier.size(width = 360.dp, height = 240.dp)
        )
    }
}
