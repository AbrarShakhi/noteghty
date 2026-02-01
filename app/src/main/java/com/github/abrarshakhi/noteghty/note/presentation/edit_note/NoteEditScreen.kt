package com.github.abrarshakhi.noteghty.note.presentation.edit_note

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.github.abrarshakhi.noteghty.R
import com.github.abrarshakhi.noteghty.core.presentation.composable.LifecycleEventsEffect
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(navController: NavController, viewModel: NoteEditViewModel, noteId: Int?) {

    val activeNote by viewModel.activeNote.collectAsStateWithLifecycle()
    val contentEditorState = rememberRichTextState()

    LaunchedEffect(activeNote.content) {
        if (contentEditorState.toMarkdown() != activeNote.content) {
            contentEditorState.setMarkdown(activeNote.content)
        }
    }

    LaunchedEffect(noteId) {
        noteId?.let { viewModel.loadNote(it) }
    }

    BackHandler {
        viewModel.tryToSave()
        navController.popBackStack()
    }

    LifecycleEventsEffect(onPause = {
        viewModel.tryToSave()
    })

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Edit Note") }, navigationIcon = {
                IconButton(onClick = {
                    viewModel.tryToSave()
                    navController.popBackStack()
                }) {
                    Icon(
                        painter = painterResource(R.drawable.outline_arrow_back_24),
                        contentDescription = "Back"
                    )
                }
            })
        }) { padding ->
        LaunchedEffect(contentEditorState) {
            snapshotFlow { contentEditorState.toMarkdown() }.collect { markdown ->
                viewModel.onContentChange(markdown)
            }
        }
        RichTextEditor(
            state = contentEditorState,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
        )
    }
}

