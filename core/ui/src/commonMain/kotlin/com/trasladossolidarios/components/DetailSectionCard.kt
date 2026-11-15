package com.trasladossolidarios.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trasladossolidarios.ui.theme.ApMobileTP2Theme
import com.trasladossolidarios.ui.theme.AppTheme

/**
 * Card con título de sección y contenido, para pantallas de detalle.
 */
@Composable
fun DetailSectionCard(
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

@Preview(showBackground = true)
@Composable
private fun DetailSectionCardPreview() {
    ApMobileTP2Theme {
        DetailSectionCard(title = "Sobre Luna") {
            Text(
                text = "Gatita gris tabulada, muy asustada.",
                style = AppTheme.typography.bodyMedium,
                color = AppTheme.colors.onSurface
            )
        }
    }
}
