package com.trasladossolidarios.shared

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.trasladossolidarios.auth.AuthViewModel
import com.trasladossolidarios.login.SessionViewModel
import com.trasladossolidarios.ui.AppRoot
import com.trasladossolidarios.ui.theme.ApMobileTP2Theme
import org.koin.compose.viewmodel.koinViewModel

/**
 * Punto de entrada de UI compartida: tema + sesión + AppRoot.
 * En Android la Activity solo hace setContent { TrasladosApp() }.
 */
@Composable
fun TrasladosApp() {
    ApMobileTP2Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val authViewModel: AuthViewModel = koinViewModel()
            val sessionViewModel: SessionViewModel = koinViewModel()

            AppRoot(
                authViewModel = authViewModel,
                sessionViewModel = sessionViewModel
            )
        }
    }
}
