package com.github.abrarshakhi.noteghty.core

import com.github.abrarshakhi.noteghty.core.presentation.navigation.AppNavDisplay
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.abrarshakhi.noteghty.core.ui.theme.NoteghtyTheme
import com.github.abrarshakhi.noteghty.note.presentation.navigation.NoteNavKey
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteghtyTheme(dynamicColor = false) {
                AppNavDisplay(startDestination = NoteNavKey.Home)
            }
        }
    }
}