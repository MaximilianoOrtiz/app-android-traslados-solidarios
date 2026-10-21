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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trasladossolidarios.auth.AuthMode
import com.trasladossolidarios.auth.AuthUiState
import com.trasladossolidarios.auth.AuthViewModel
import com.trasladossolidarios.ui.theme.ApMobileTP2Theme
import components.AppTextField
import components.PrimaryButton

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

    Scaffold(
        modifier = modifier,
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

                        Text(
                            text = "Huella",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Conectamos personas para ayudar a animales.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                },
                expandedHeight = 175.dp,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF009688),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isRegisterMode) {
                    "Unite a Huella"
                } else {
                    "Bienvenido/a a Huella"
                },
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF171717)
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = if (isRegisterMode) {
                    "Creá tu cuenta y ayudá a los animales."
                } else {
                    "Iniciá sesión para ver los traslados cerca tuyo."
                },
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF777777)
            )

            Spacer(Modifier.height(22.dp))

            if (isRegisterMode) {
                AppTextField(
                    value = state.name,
                    onValueChange = onNameChange,
                    label = "Nombre",
                    )


                Spacer(Modifier.height(14.dp))

                AppTextField(
                    value = state.surname,
                    onValueChange = onSurnameChange,
                    label = "Apellido",

                    )

                Spacer(Modifier.height(14.dp))
            }

            AppTextField(
                value = state.email,
                onValueChange = onEmailChange,
                label = "Email",
                keyboardType = KeyboardType.Email
            )

            Spacer(Modifier.height(14.dp))

            AppTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                label = "Contraseña",
                isPassword = true
            )

            if (!isRegisterMode) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        color = Color(0xFF009688),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            state.error?.let { message ->
                Spacer(Modifier.height(12.dp))

                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(20.dp))

            PrimaryButton(
                text = if (isRegisterMode) {
                    "Crear cuenta"
                } else {
                    "Iniciar sesión"
                },
                onClick = {
                    if (isRegisterMode) {
                        onRegisterClick()
                    } else {
                        onLoginClick()
                    }
                },
                isLoading = state.isLoading
            )

            Spacer(Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = Color(0xFFE0E0E0)
                )

                Text(
                    text = "  o  ",
                    color = Color(0xFF888888),
                    style = MaterialTheme.typography.bodySmall
                )

                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = Color(0xFFE0E0E0)
                )
            }

            Spacer(Modifier.height(14.dp))

            OutlinedButton(
                onClick = onToggleAuthMode,
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(28.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.Transparent
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color(0xFFEFFFFB),
                    contentColor = Color(0xFF00796B)
                )
            ) {
                Text(
                    text = if (isRegisterMode) {
                        "Ya tengo una cuenta"
                    } else {
                        "Crear una cuenta nueva"
                    }
                )
            }

            Spacer(Modifier.height(26.dp))

            Text(
                text = "Al usar Huella aceptás nuestros Términos de uso y Política de privacidad.",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF999999),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(
    name = "Login",
    showBackground = true,
    showSystemUi = true
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
