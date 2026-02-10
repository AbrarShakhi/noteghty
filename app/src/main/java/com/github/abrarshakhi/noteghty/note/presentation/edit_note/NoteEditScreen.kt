package com.github.abrarshakhi.noteghty.note.presentation.edit_note

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.github.abrarshakhi.noteghty.note.presentation.edit_note.composable.NoteEditorTopBar
import kotlinx.coroutines.flow.Flow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    noteId: Long?,
    state: NoteEditState,
    effect: Flow<NoteEditEffect>,
    onIntent: (NoteEditIntent) -> Unit,
    onBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(noteId) {
        onIntent(NoteEditIntent.Load(noteId = noteId))
    }
    LaunchedEffect(Unit) {
        effect.collect { it ->
            when (it) {
                is NoteEditEffect.Error -> snackbarHostState.showSnackbar(it.message)
                is NoteEditEffect.SavedSuccessfulAndReadyToGoBack -> onBack()
            }
        }
    }
    BackHandler {
        onIntent(NoteEditIntent.SaveAsynchronous)
        onBack()
    }

    Scaffold(
        topBar = { NoteEditorTopBar() },
        snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->

        if (state.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth()
            )
        }

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .imePadding()
        ) {}
    }
}

