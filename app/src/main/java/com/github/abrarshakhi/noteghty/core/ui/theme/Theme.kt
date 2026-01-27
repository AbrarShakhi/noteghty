package com.github.abrarshakhi.noteghty.core.ui.theme

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
    primary = NoteghtyOrange,
    onPrimary = MonokaiBackground,
    background = MonokaiWhite,
    onBackground = MonokaiBackground,
    surface = MonokaiWhite,
    onSurface = MonokaiBackground
)

private val DarkColorScheme = darkColorScheme(
    primary = NoteghtyOrange,
    onPrimary = MonokaiBackground,
    background = MonokaiBackground,
    onBackground = MonokaiWhite,
    surface = MonokaiBackground,
    onSurface = MonokaiWhite
)


@Composable
fun GuittarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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
        typography = NoteghtyTypography,
        content = content
    )
}