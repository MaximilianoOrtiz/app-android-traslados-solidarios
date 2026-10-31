package com.trasladossolidarios.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trasladossolidarios.ui.theme.ApMobileTP2Theme
import com.trasladossolidarios.ui.theme.AppTheme
import com.trasladossolidarios.ui.theme.Pending
import com.trasladossolidarios.ui.theme.PendingContainer

/**
 * Banner de advertencia con fondo amarillo e ícono, para notas importantes.
 */
@Composable
fun WarningBanner(
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

@Preview(showBackground = true)
@Composable
private fun WarningBannerPreview() {
    ApMobileTP2Theme {
        WarningBanner(
            text = "Necesita transportadora obligatoriamente. Está asustada, requiere manejo suave."
        )
    }
}
