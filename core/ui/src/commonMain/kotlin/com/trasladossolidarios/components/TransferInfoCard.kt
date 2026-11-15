package com.trasladossolidarios.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trasladossolidarios.domain.model.Location
import com.trasladossolidarios.ui.theme.ApMobileTP2Theme
import com.trasladossolidarios.ui.theme.AppTheme
import com.trasladossolidarios.ui.resources.Res
import com.trasladossolidarios.ui.resources.transfer_info_card_title
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Instant

/**
 * Card "Información del traslado": timeline origen → destino
 * y fecha del traslado.
 */
@Composable
fun TransferInfoCard(
    origin: Location,
    destination: Location,
    dateOfTransfer: Instant,
    modifier: Modifier = Modifier
) {
    DetailSectionCard(
        title = stringResource(Res.string.transfer_info_card_title),
        modifier = modifier
    ) {
        TransferTimeline(origin = origin, destination = destination)
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
                text = formatTransferDateTime(dateOfTransfer),
                style = AppTheme.typography.bodyMedium,
                color = AppTheme.colors.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TransferInfoCardPreview() {
    ApMobileTP2Theme {
        TransferInfoCard(
            origin = Location(latitude = null, longitude = null, address = "Villa del Parque, CABA"),
            destination = Location(
                latitude = null,
                longitude = null,
                address = "Clínica Veterinaria Mascotas - Palermo"
            ),
            dateOfTransfer = Clock.System.now() + 86_400.seconds
        )
    }
}
