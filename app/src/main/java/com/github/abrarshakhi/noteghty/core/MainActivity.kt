package com.github.abrarshakhi.noteghty.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.abrarshakhi.noteghty.core.presentation.navigation.AppNavDisplay
import com.github.abrarshakhi.noteghty.core.presentation.navigation.AppNavGraph
import com.github.abrarshakhi.noteghty.core.ui.theme.NoteghtyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteghtyTheme(dynamicColor = false) {
                AppNavDisplay(startDestination = AppNavGraph.NoteNavKey)
            }
        }
    }
}