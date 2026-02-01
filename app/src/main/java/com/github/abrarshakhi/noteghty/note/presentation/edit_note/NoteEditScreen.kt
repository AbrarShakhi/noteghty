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

    LaunchedEffect(noteId) {
        if (noteId != null) {
            viewModel.getNote(noteId)
        }
    }

    val activeNote by viewModel.activeNote.collectAsStateWithLifecycle()
    val richTextState = rememberRichTextState()
    richTextState.setText(activeNote.content)

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
        RichTextEditor(
            state = richTextState,
            modifier = Modifier.padding(padding).fillMaxSize(),
        )
    }
}

