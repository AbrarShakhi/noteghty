package com.github.abrarshakhi.noteghty.core

import AppNavGraph
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.github.abrarshakhi.noteghty.core.ui.theme.NoteghtyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteghtyTheme(
                dynamicColor = false
            ) { AppNavGraph(navController = rememberNavController()) }
        }
    }
}