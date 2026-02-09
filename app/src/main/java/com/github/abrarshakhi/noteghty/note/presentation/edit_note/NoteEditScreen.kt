package com.github.abrarshakhi.noteghty.note.presentation.edit_note

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import com.github.abrarshakhi.noteghty.note.presentation.edit_note.composable.NoteEditorTopBar
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
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
    val titleEditorState = rememberRichTextState()
    val contentEditorState = rememberRichTextState()

    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager: FocusManager = LocalFocusManager.current

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
    LaunchedEffect(titleEditorState.toText()) {
        snapshotFlow { titleEditorState.toText() }.collect { newText ->
            Log.d("EDITOR", newText)
        }
    }
    BackHandler {
        onIntent(NoteEditIntent.SaveForcedAndNotify)
    }

    Scaffold(
        topBar = { NoteEditorTopBar() },
        snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .imePadding()
        ) {
            RichTextEditor(
                state = titleEditorState,
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.titleLarge,
                singleLine = true,
                label = {},
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                })
            )
            RichTextEditor(
                state = contentEditorState,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

