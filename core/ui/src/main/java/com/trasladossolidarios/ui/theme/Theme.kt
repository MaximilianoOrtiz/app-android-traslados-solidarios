package com.trasladossolidarios.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    // Primary: turquesa
    primary = Teal40,
    onPrimary = White,
    primaryContainer = Teal90,
    onPrimaryContainer = Teal10,

    // Secondary: verde menta
    secondary = Mint40,
    onSecondary = White,
    secondaryContainer = Mint90,
    onSecondaryContainer = Mint10,

    // Tertiary: naranja
    tertiary = Orange40,
    onTertiary = White,
    tertiaryContainer = Orange90,
    onTertiaryContainer = Orange10,

    // Error
    error = Red40,
    onError = White,
    errorContainer = Red90,
    onErrorContainer = Red10,

    // Background y superficies
    background = Neutral99,
    onBackground = Neutral10,

    surface = Neutral99,
    onSurface = Neutral10,

    surfaceVariant = Neutral96,
    onSurfaceVariant = Neutral40,

    surfaceDim = Neutral87,
    surfaceBright = Neutral99,
    surfaceContainerLowest = White,
    surfaceContainerLow = Neutral96,
    surfaceContainer = Neutral95,
    surfaceContainerHigh = Neutral92,
    surfaceContainerHighest = Neutral90,

    // Bordes y divisores
    outline = Outline,
    outlineVariant = OutlineVariant,

    // Colores inversos
    inverseSurface = Neutral20,
    inverseOnSurface = Neutral95,
    inversePrimary = Teal80,

    scrim = Black
)

private val DarkColorScheme = darkColorScheme(
    // Primary: turquesa
    primary = Teal80,
    onPrimary = Teal10,
    primaryContainer = Teal30,
    onPrimaryContainer = Teal90,

    // Secondary: verde menta
    secondary = Mint80,
    onSecondary = Mint10,
    secondaryContainer = Mint30,
    onSecondaryContainer = Mint90,

    // Tertiary: naranja
    tertiary = Orange80,
    onTertiary = Orange10,
    tertiaryContainer = Orange30,
    onTertiaryContainer = Orange90,

    // Error
    error = Red80,
    onError = Red10,
    errorContainer = Red40,
    onErrorContainer = Red90,

    // Background y superficies
    background = Neutral10,
    onBackground = Neutral90,

    surface = Neutral10,
    onSurface = Neutral90,

    surfaceVariant = Neutral20,
    onSurfaceVariant = Neutral90,

    surfaceDim = Neutral6,
    surfaceBright = Neutral20,
    surfaceContainerLowest = Neutral6,
    surfaceContainerLow = Neutral10,
    surfaceContainer = Neutral10,
    surfaceContainerHigh = Neutral20,
    surfaceContainerHighest = Neutral40,

    // Bordes y divisores
    outline = Neutral60,
    outlineVariant = Neutral40,

    // Colores inversos
    inverseSurface = Neutral90,
    inverseOnSurface = Neutral10,
    inversePrimary = Teal40,

    scrim = Black
)


@Composable
fun ApMobileTP2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}