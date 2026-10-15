package com.trasladossolidarios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.trasladossolidarios.auth.AuthViewModel
import com.trasladossolidarios.login.SessionViewModel
import com.trasladossolidarios.ui.AppRoot
import com.trasladossolidarios.ui.theme.ApMobileTP2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val container = (application as ComposeTrasladosSolidariosApp).container
        setContent {
            ApMobileTP2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val authViewModel: AuthViewModel = viewModel(
                        factory = container.authViewModelFactory()
                    )

                    val sessionViewModel: SessionViewModel = viewModel(
                        factory = container.sessionViewModelFactory()
                    )

                    AppRoot(
                        container = container,
                        authViewModel = authViewModel,
                        sessionViewModel = sessionViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ApMobileTP2Theme {
        Greeting("Android")
    }
}