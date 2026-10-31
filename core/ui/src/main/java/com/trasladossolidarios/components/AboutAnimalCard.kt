package com.trasladossolidarios.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.trasladossolidarios.domain.enum.AnimalSpecies
import com.trasladossolidarios.domain.model.Animal
import com.trasladossolidarios.ui.theme.ApMobileTP2Theme
import com.trasladossolidarios.ui.theme.AppTheme
import com.ui.R

/**
 * Card "Sobre {nombre}": descripción del animal y, si hay notas,
 * un [WarningBanner] con las advertencias de manejo.
 */
@Composable
fun AboutAnimalCard(
    animal: Animal,
    notes: String = "",
    modifier: Modifier = Modifier
) {
    val animalName = animal.name.ifBlank {
        stringResource(R.string.ride_card_animal_name_fallback)
    }

    DetailSectionCard(
        title = stringResource(R.string.about_animal_card_title, animalName),
        modifier = modifier
    ) {
        AnimalAboutContent(animal = animal, notes = notes)
    }
}

@Composable
private fun ColumnScope.AnimalAboutContent(animal: Animal, notes: String) {
    Text(
        text = animal.description,
        style = AppTheme.typography.bodyMedium,
        color = AppTheme.colors.onSurface
    )

    if (notes.isNotBlank()) {
        Spacer(modifier = Modifier.height(AppTheme.spacing.md))
        WarningBanner(text = notes)
    }
}

@Preview(showBackground = true)
@Composable
private fun AboutAnimalCardPreview() {
    ApMobileTP2Theme {
        AboutAnimalCard(
            animal = Animal(
                name = "Luna",
                species = AnimalSpecies.CAT,
                description = "Gatita gris tabulada, muy asustada. Fue encontrada bajo un auto en el barrio",
                photoUrl = null
            ),
            notes = "Necesita transportadora obligatoriamente. Está asustada, requiere manejo suave."
        )
    }
}
