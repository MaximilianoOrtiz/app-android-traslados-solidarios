package com.trasladossolidarios.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trasladossolidarios.ui.theme.ApMobileTP2Theme
import com.trasladossolidarios.ui.theme.AppTheme


@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    val colors = AppTheme.colors

    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = AppTheme.shapes.extraLarge,
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.tertiary,
            contentColor = colors.onTertiary
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                color = colors.onTertiary,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                style = AppTheme.typography.labelLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonPreview(){
    ApMobileTP2Theme() {
        PrimaryButton(
            text = "Hola",
            onClick = {},
            enabled = false,
            isLoading = false
        )

    }
}