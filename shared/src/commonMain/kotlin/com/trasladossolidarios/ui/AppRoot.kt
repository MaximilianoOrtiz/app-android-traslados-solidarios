package com.trasladossolidarios.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.trasladossolidarios.auth.AuthViewModel
import com.trasladossolidarios.login.SessionState
import com.trasladossolidarios.login.SessionViewModel
import com.trasladossolidarios.login.LoginScrennRoute

@Composable
fun AppRoot(
    sessionViewModel: SessionViewModel,
    authViewModel: AuthViewModel
) {
    val session by sessionViewModel.state.collectAsState()

    when (session) {
        SessionState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        SessionState.Unauthenticated -> {
            LoginScrennRoute(
                viewModel = authViewModel
            )
        }

        is SessionState.Authenticated -> {
            AppShell(
                currentUserId = (session as SessionState.Authenticated).user.uid
            )
        }
    }
}