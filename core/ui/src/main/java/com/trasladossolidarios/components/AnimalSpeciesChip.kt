package com.trasladossolidarios.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trasladossolidarios.domain.enum.AnimalSpecies
import com.trasladossolidarios.ui.theme.ApMobileTP2Theme
import com.trasladossolidarios.ui.theme.AppTheme
import com.ui.R

/**
 * Píldora con el ícono y la traducción de la especie del animal.
 */
@Composable
fun AnimalSpeciesChip(
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

@Preview(showBackground = true)
@Composable
private fun AnimalSpeciesChipPreview() {
    ApMobileTP2Theme {
        Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.sm)) {
            AnimalSpeciesChip(species = AnimalSpecies.CAT)
            AnimalSpeciesChip(species = AnimalSpecies.DOG)
        }
    }
}
