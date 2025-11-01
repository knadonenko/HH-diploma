package ru.practicum.android.diploma.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = blue,
    onPrimary = white,
    background = blackUniversal,
    onBackground = white,
    surface = blackUniversal,
    onSurface = white,
    surfaceVariant = grey500,
    onSurfaceVariant = white,
    secondary = blue,
    onSecondary = white,
    outline = grey200,
    surfaceTint = blue,
)

private val LightColorScheme = lightColorScheme(
    primary = blue,
    onPrimary = white,
    background = white,
    onBackground = blackUniversal,
    surface = white,
    onSurface = blackUniversal,
    surfaceVariant = grey200,
    onSurfaceVariant = grey500,
    secondary = blue,
    onSecondary = white,
    outline = grey200,
    surfaceTint = blue,
)

val LocalTypography = staticCompositionLocalOf<DiplomaTypography> {
    error("No CustomTypography provided")
}

@Composable
fun DiplomaAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    ThemedSystemBars(isDarkTheme = darkTheme)

    CompositionLocalProvider(
        LocalTypography provides Typography
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}

@Composable
fun ThemedSystemBars(isDarkTheme: Boolean) {
    val view = LocalView.current
    val window = (view.context as Activity).window

    SideEffect {
        val windowInsetsController = WindowCompat.getInsetsController(window, view)

        // Отключаем поведение по умолчанию
        windowInsetsController.isAppearanceLightStatusBars = !isDarkTheme
        windowInsetsController.isAppearanceLightNavigationBars = !isDarkTheme

        // Прозрачные системные бары
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}
