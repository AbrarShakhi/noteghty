package com.github.abrarshakhi.noteghty.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color.White,
    background = DarkGray,
    onBackground = Color.White,
    surface = LightBlue,
    onSurface = DarkGray
)

@Composable
fun NoteghtyTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = NoteghtyTypography,
        shapes = Shapes,
        content = content
    )
}
