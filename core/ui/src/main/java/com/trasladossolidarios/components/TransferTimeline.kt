package com.trasladossolidarios.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trasladossolidarios.domain.model.Location
import com.trasladossolidarios.ui.theme.ApMobileTP2Theme
import com.trasladossolidarios.ui.theme.AppTheme
import com.ui.R

/**
 * Timeline vertical origen → destino con dots y conector.
 */
@Composable
fun TransferTimeline(
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

@Preview(showBackground = true)
@Composable
private fun TransferTimelinePreview() {
    ApMobileTP2Theme {
        TransferTimeline(
            origin = Location(latitude = null, longitude = null, address = "Villa del Parque, CABA"),
            destination = Location(
                latitude = null,
                longitude = null,
                address = "Clínica Veterinaria Mascotas - Veterinaria - Palermo"
            )
        )
    }
}
