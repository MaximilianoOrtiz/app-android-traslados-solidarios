package com.trasladossolidarios.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trasladossolidarios.domain.model.User
import com.trasladossolidarios.domain.model.getFullName
import com.trasladossolidarios.ui.theme.ApMobileTP2Theme
import com.trasladossolidarios.ui.theme.AppTheme
import com.ui.R

/**
 * Avatar con inicial + "Publicado por {nombre}".
 */
@Composable
fun PublisherRow(
    owner: User,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(AppTheme.colors.primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${owner.firstName.firstOrNull()?.uppercase() ?: ""}" +
                        "${owner.lastName.firstOrNull()?.uppercase() ?: ""}",
                style = AppTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = AppTheme.colors.onPrimary
            )
        }

        Text(
            text = stringResource(R.string.ride_detail_publisher, owner.getFullName()),
            style = AppTheme.typography.bodyMedium,
            color = AppTheme.colors.onSurfaceVariant
        )
    }
}
@Preview(showBackground = true)
@Composable
private fun PublisherRowPreview() {
    ApMobileTP2Theme {
        PublisherRow(
            owner = User(
                uid = "123",
                firstName = "Carlos",
                lastName = "Ruiz",
                email = "carlos@email.com"
            )
        )
    }
}