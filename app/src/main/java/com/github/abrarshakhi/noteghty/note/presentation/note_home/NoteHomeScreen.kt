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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.abrarshakhi.noteghty.R
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.model.NoteViewStyle
import com.github.abrarshakhi.noteghty.note.presentation.note_home.composable.EmptyNotesList
import com.github.abrarshakhi.noteghty.note.presentation.note_home.composable.NoteItem
import com.github.abrarshakhi.noteghty.note.presentation.note_home.composable.NoteOrderBottomSheet
import com.github.abrarshakhi.noteghty.note.presentation.note_home.composable.NotesList
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteHomeScreen(
    state: NoteHomeState,
    effect: Flow<NoteHomeEffect>,
    onIntent: (NoteHomeIntent) -> Unit,
    onEditNoteNavigation: (Note?) -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    var showSortSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        effect.collect { it ->
            when (it) {
                is NoteHomeEffect.Error -> snackBarHostState.showSnackbar(it.message)
            }
        }
    }

    Scaffold(modifier = Modifier.shadow(100.dp), topBar = {
        TopAppBar(title = { Text(text = stringResource(R.string.app_name)) }, navigationIcon = {
            Box(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.noteghty),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .width(36.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant, shape = CircleShape
                        )
                        .padding(5.dp)
                )
            }
        }, actions = {
            IconButton(onClick = { showSortSheet = true }) {
                Icon(
                    painter = painterResource(R.drawable.outline_swap_vert_24),
                    contentDescription = "Order"
                )
            }

            IconButton(onClick = { onIntent(NoteHomeIntent.ToggleViewStyle) }) {
                Icon(
                    painter = painterResource(
                        when (state.viewStyle) {
                            NoteViewStyle.COZY -> R.drawable.outline_view_agenda_24
                            NoteViewStyle.AGENDA -> R.drawable.outline_view_cozy_24
                        }
                    ), contentDescription = "Rearrange"
                )
            }
        })
    }, floatingActionButton = {
        if (state.notes.isNotEmpty()) {
            FloatingActionButton(onClick = { onEditNoteNavigation(null) }, shape = CircleShape) {
                Icon(
                    painter = painterResource(R.drawable.outline_edit_square_24),
                    contentDescription = "add new note",
                )
            }
        }
    }, snackbarHost = { SnackbarHost(snackBarHostState) }) { padding ->

        if (state.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth()
            )
        }

        if (state.notes.isEmpty()) {
            EmptyNotesList(padding = padding, onNewNote = { onEditNoteNavigation(null) })
        } else {
            NotesList(state.viewStyle, state.notes, padding) { note ->
                NoteItem(note = note, onClick = onEditNoteNavigation)
            }
        }
    }

    if (showSortSheet) {
        NoteOrderBottomSheet(
            currentOrder = state.noteOrder,
            onDismiss = { showSortSheet = false },
            onSave = { order ->
                onIntent(NoteHomeIntent.SetNoteOrderingSettings(order))
                onIntent(NoteHomeIntent.LoadNotes)
                showSortSheet = false
            })
    }
}

