package com.trasladossolidarios.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trasladossolidarios.auth.AuthMode
import com.trasladossolidarios.auth.AuthUiState
import com.trasladossolidarios.auth.AuthViewModel
import com.trasladossolidarios.auth.R
import com.trasladossolidarios.ui.theme.ApMobileTP2Theme
import com.trasladossolidarios.components.AppTextField
import com.trasladossolidarios.components.PrimaryButton
import com.trasladossolidarios.ui.theme.AppTheme

@Composable
fun LoginScrennRoute(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    // collectAsStateWithLifecycle deja de escuchar cuando la app pasa a background.
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        state = uiState,
        onNameChange = viewModel::onNameChange,
        onSurnameChange = viewModel::onSurnameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = viewModel::login,
        onRegisterClick = viewModel::register,
        onToggleAuthMode = viewModel::toggleAuthMode,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun LoginScreen(
    state: AuthUiState,
    onNameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onToggleAuthMode: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isRegisterMode = state.mode == AuthMode.REGISTER
    val colors = AppTheme.colors
    val spacing = AppTheme.spacing
    val typography = AppTheme.typography
    val shapes = AppTheme.shapes

    Scaffold(
        modifier = modifier,
        containerColor = colors.background,
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Pets,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(Modifier.height(spacing.md))
                        Text(
                            text = stringResource(R.string.name_app),
                            style = AppTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(spacing.md))
                        Text(
                            text = stringResource(R.string.login_header_subtitle),
                            style = AppTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                expandedHeight = 175.dp,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppTheme.colors.primary,
                    titleContentColor = AppTheme.colors.onPrimary
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = spacing.lg,
                    vertical = spacing.md
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(
                    if (isRegisterMode) {
                        R.string.register_welcome_title
                    } else {
                        R.string.login_welcome_title
                    }
                ),
                style = typography.titleLarge,
                color = colors.onBackground
            )

            Spacer(Modifier.height(spacing.xs))

            Text(
                text = stringResource(
                    if (isRegisterMode) {
                        R.string.register_subtitle
                    } else {
                        R.string.login_subtitle
                    }
                ),
                style = typography.bodyMedium,
                color = colors.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(spacing.lg))

            if (isRegisterMode) {
                AppTextField(
                    value = state.name,
                    onValueChange = onNameChange,
                    label = stringResource(R.string.field_name)
                )

                Spacer(Modifier.height(spacing.sm + spacing.xs + spacing.xs))

                AppTextField(
                    value = state.surname,
                    onValueChange = onSurnameChange,
                    label = stringResource(R.string.field_surname)
                )

                Spacer(Modifier.height(spacing.sm + spacing.xs + spacing.xs))
            }

            AppTextField(
                value = state.email,
                onValueChange = onEmailChange,
                label = stringResource(R.string.field_email),
                keyboardType = KeyboardType.Email
            )

            Spacer(Modifier.height(spacing.sm + spacing.xs + spacing.xs))

            AppTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                label = stringResource(R.string.field_password),
                isPassword = true
            )

            if (!isRegisterMode) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = spacing.sm + spacing.xs),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = stringResource(R.string.forgot_password),
                        color = colors.primary,
                        style = typography.bodyMedium
                    )
                }
            }

            state.error?.let { message ->
                Spacer(Modifier.height(spacing.sm + spacing.xs))

                Text(
                    text = message,
                    color = colors.error,
                    style = typography.bodyMedium
                )
            }

            Spacer(Modifier.height(spacing.lg - spacing.xs))

            PrimaryButton(
                text = stringResource(
                    if (isRegisterMode) {
                        R.string.register_button
                    } else {
                        R.string.login_button
                    }
                ),
                onClick = {
                    if (isRegisterMode) {
                        onRegisterClick()
                    } else {
                        onLoginClick()
                    }
                },
                isLoading = state.isLoading
            )

            Spacer(Modifier.height(spacing.md + spacing.xs / 2))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = colors.outlineVariant
                )

                Text(
                    text = "  ${stringResource(R.string.separator_or)}  ",
                    color = colors.onSurfaceVariant,
                    style = typography.bodySmall
                )

                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = colors.outlineVariant
                )
            }

            Spacer(Modifier.height(spacing.sm + spacing.xs + spacing.xs))

            OutlinedButton(
                onClick = onToggleAuthMode,
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = shapes.extraLarge,
                border = BorderStroke(
                    width = 1.dp,
                    color = colors.outlineVariant
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = colors.secondaryContainer,
                    contentColor = colors.onSecondaryContainer
                )
            ) {
                Text(
                    text = stringResource(
                        if (isRegisterMode) {
                            R.string.register_toggle_login
                        } else {
                            R.string.login_toggle_register
                        }
                    ),
                    style = typography.labelLarge
                )
            }

            Spacer(Modifier.height(spacing.lg))

            Text(
                text = stringResource(R.string.login_legal_notice),
                style = typography.bodySmall,
                color = colors.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}



@Preview(
    name = "Login",
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun LoginScreenPreview() {
    ApMobileTP2Theme {
        LoginScreen(
            state = AuthUiState(
                mode = AuthMode.LOGIN,
                name = "",
                surname = "",
                email = "maxiortiz_22@hotmail.com",
                password = "password",
                isLoading = false,
                error = null
            ),
            onNameChange = {},
            onSurnameChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            onRegisterClick = {},
            onToggleAuthMode = {}
        )
    }
}
