package com.github.abrarshakhi.noteghty.note.presentation.edit_note

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import kotlinx.coroutines.flow.Flow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    state: NoteEditState, effect: Flow<NoteEditEffect>, onIntent: (NoteEditIntent) -> Unit
) {
    val contentEditorState = rememberRichTextState()
    val titleEditorState = rememberRichTextState()

    Scaffold(topBar = {}) { padding ->
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
            )
            RichTextEditor(
                state = contentEditorState,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

