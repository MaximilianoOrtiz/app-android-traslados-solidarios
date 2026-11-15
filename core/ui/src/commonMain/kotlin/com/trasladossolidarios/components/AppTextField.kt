package com.trasladossolidarios.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.trasladossolidarios.ui.theme.AppTheme

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    val colors = AppTheme.colors
    val spacing = AppTheme.spacing

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            style = AppTheme.typography.labelMedium,
            color = colors.onSurface
        )

        Spacer(
            modifier = Modifier.height(spacing.xs + spacing.xs / 2)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = AppTheme.shapes.medium,
            visualTransformation = if (isPassword) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPassword) {
                    KeyboardType.Password
                } else {
                    keyboardType
                },
                imeAction = ImeAction.Next
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colors.primary,
                unfocusedBorderColor = colors.outline,
                focusedTextColor = colors.onSurface,
                unfocusedTextColor = colors.onSurface,
                cursorColor = colors.primary,
                focusedLabelColor = colors.primary,
                unfocusedLabelColor = colors.onSurfaceVariant
            ),
            label = null
        )
    }
}



