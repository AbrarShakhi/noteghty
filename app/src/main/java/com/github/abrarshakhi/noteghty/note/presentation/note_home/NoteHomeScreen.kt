package com.github.abrarshakhi.noteghty.note.presentation.note_home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.github.abrarshakhi.noteghty.R
import com.github.abrarshakhi.noteghty.core.presentation.state.UiState
import com.github.abrarshakhi.noteghty.note.domain.model.NoteViewStyle
import com.github.abrarshakhi.noteghty.note.presentation.navigation.NoteRoute
import com.github.abrarshakhi.noteghty.note.presentation.note_home.composable.EmptyNotesList
import com.github.abrarshakhi.noteghty.note.presentation.note_home.composable.ErrorNotesList
import com.github.abrarshakhi.noteghty.note.presentation.note_home.composable.NoteItem
import com.github.abrarshakhi.noteghty.note.presentation.note_home.composable.NoteOrderBottomSheet
import com.github.abrarshakhi.noteghty.note.presentation.note_home.composable.NotesList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteHomeScreen(navController: NavController, viewModel: NoteHomeViewModel) {

    val notesListState by viewModel.notesState.collectAsStateWithLifecycle()
    val viewStyle by viewModel.viewStyle.collectAsStateWithLifecycle()

    var showSortSheet by remember { mutableStateOf(false) }


    val onSave = {
        navController.navigate(NoteRoute.Edit(-1).route())
    }

    Scaffold(modifier = Modifier.shadow(100.dp), topBar = {
        TopAppBar(title = { Text(text = stringResource(R.string.app_name)) }, navigationIcon = {
            Box(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.noteghty),
                    contentDescription = "App Logo",
                    modifier = Modifier.width(36.dp).background(
                        color = MaterialTheme.colorScheme.surfaceVariant, shape = CircleShape
                    ).padding(5.dp)
                )
            }
        }, actions = {
            IconButton(onClick = { showSortSheet = true }) {
                Icon(
                    painter = painterResource(R.drawable.outline_swap_vert_24),
                    contentDescription = "Order"
                )
            }

            IconButton(onClick = { viewModel.toggleViewStyle() }) {
                Icon(
                    painter = painterResource(
                        when (viewStyle) {
                            NoteViewStyle.COZY -> R.drawable.outline_view_agenda_24
                            NoteViewStyle.AGENDA -> R.drawable.outline_view_cozy_24
                        }
                    ), contentDescription = "Rearrange"
                )
            }
        })
    }, floatingActionButton = {
        when (val state = notesListState.content) {
            is UiState.UiContent.Data -> if (state.value.isNotEmpty()) {
                FloatingActionButton(onClick = onSave, shape = CircleShape) {
                    Icon(
                        painter = painterResource(R.drawable.outline_edit_square_24),
                        contentDescription = "add new note",
                    )
                }
            }

            is UiState.UiContent.Error -> {}
        }
    }) { padding ->

        if (notesListState.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.padding(padding).fillMaxWidth()
            )
        }

        when (val state = notesListState.content) {
            is UiState.UiContent.Data -> {
                val notes = state.value
                if (notes.isEmpty()) {
                    EmptyNotesList(padding, onSave)
                } else {
                    NotesList(viewStyle, notes, padding) { note ->
                        NoteItem(note = note, onClick = {
                            navController.navigate(NoteRoute.Edit(note.id ?: -1).route())
                        })
                    }
                }
            }

            is UiState.UiContent.Error -> {
                ErrorNotesList(padding, state.message) { viewModel.loadNotes() }
            }
        }
    }

    if (showSortSheet) {
        NoteOrderBottomSheet(
            currentOrder = viewModel.noteOrderingSettings.collectAsStateWithLifecycle().value,
            onDismiss = { showSortSheet = false },
            onSave = { order ->
                viewModel.setNoteOrderingSettings(order)
                viewModel.loadNotes()
                showSortSheet = false
            })
    }
}

