package com.github.abrarshakhi.noteghty.note.presentation.edit_note

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.mohamedrejeb.richeditor.model.rememberRichTextState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(navController: NavController, viewModel: NoteEditViewModel) {
    val richTextState = rememberRichTextState()

    BackHandler { viewModel.tryToSaveHtml(richTextState.toHtml()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Note") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding))
    }
}

