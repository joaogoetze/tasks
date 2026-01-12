package com.example.tasks.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryGreen,
    onPrimary = White,
    secondary = PrimaryGreenSoft,
    surface = PrimaryGreen,
    onSurface = White,
    background = Background,
    onBackground = Gray,
    surfaceContainer = PrimaryGreen,
    primaryContainer = PrimaryGreen,
    onPrimaryContainer = White,
    secondaryContainer = PrimaryGreen,
    onSecondaryContainer = White,
    onSurfaceVariant = Gray
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = White,
    secondary = PrimaryGreenSoft,
    surface = PrimaryGreen,
    onSurface = White,
    background = Background,
    onBackground = Gray,
    surfaceContainer = PrimaryGreen,
    primaryContainer = PrimaryGreen,
    onPrimaryContainer = White,
    secondaryContainer = PrimaryGreen,
    onSecondaryContainer = White,
    onSurfaceVariant = Gray

    /* Other default colors to override
    onSecondary = Color.White,
    onTertiary = Color.White,
    */
)

@Composable
fun TasksTheme(
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
        typography = Typography,
        content = content
    )
}