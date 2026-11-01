package com.trasladossolidarios.openrides.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.trasladossolidarios.components.AppTextField
import com.trasladossolidarios.components.PrimaryButton
import com.trasladossolidarios.openrides.R
import com.trasladossolidarios.ui.theme.ApMobileTP2Theme
import com.trasladossolidarios.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplyToRideSheet(
    isSubmitting: Boolean,
    onSubmit: (message: String, contact: String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var contact by rememberSaveable { mutableStateOf("") }
    var message by rememberSaveable { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = AppTheme.colors.surface,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.spacing.md)
                .padding(bottom = AppTheme.spacing.lg)
                .navigationBarsPadding()
        ) {
            Text(
                text = stringResource(R.string.ride_detail_apply_sheet_title),
                style = AppTheme.typography.headlineSmall,
                color = AppTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(AppTheme.spacing.xs))

            Text(
                text = stringResource(R.string.ride_detail_apply_sheet_subtitle),
                style = AppTheme.typography.bodyMedium,
                color = AppTheme.colors.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(AppTheme.spacing.lg))

            AppTextField(
                value = contact,
                onValueChange = { contact = it },
                label = stringResource(R.string.ride_detail_apply_contact_label),
                keyboardType = KeyboardType.Phone
            )

            Spacer(modifier = Modifier.height(AppTheme.spacing.md))

            AppTextField(
                value = message,
                onValueChange = { message = it },
                label = stringResource(R.string.ride_detail_apply_message_label),
                keyboardType = KeyboardType.Text
            )

            Spacer(modifier = Modifier.height(AppTheme.spacing.lg))

            PrimaryButton(
                text = stringResource(R.string.ride_detail_apply_submit),
                onClick = { onSubmit(message, contact) },
                enabled = contact.isNotBlank(),
                isLoading = isSubmitting,
                modifier = Modifier.fillMaxWidth()
            )

            TextButton(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(R.string.ride_detail_apply_cancel),
                    style = AppTheme.typography.labelLarge,
                    color = AppTheme.colors.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ApplyToRideSheetPreview() {
    ApMobileTP2Theme {
        ApplyToRideSheet(
            isSubmitting = false,
            onSubmit = { _, _ -> },
            onDismiss = {}
        )
    }
}
