package com.github.abrarshakhi.noteghty.note.presentation.note_home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.github.abrarshakhi.noteghty.R
import com.github.abrarshakhi.noteghty.core.ui.state.UiState
import com.github.abrarshakhi.noteghty.note.presentation.navigation.NoteRoute
import com.github.abrarshakhi.noteghty.note.presentation.note_home.NoteHomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteHomeScreen(navController: NavController, viewModel: NoteHomeViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                navigationIcon = {
                    Box(modifier = Modifier.padding(start = 12.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.noteghty),
                            contentDescription = "App Logo",
                            modifier = Modifier
                                .width(36.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = CircleShape
                                )
                                .padding(5.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* sort */ }) {
                        Icon(
                            painter = painterResource(R.drawable.outline_swap_vert_24),
                            contentDescription = "Sort"
                        )
                    }

                    IconButton(onClick = { /* rearrange */ }) {
                        Icon(
                            painter = painterResource(R.drawable.outline_view_agenda_24),
                            contentDescription = "Rearrange"
                        )
                    }
                }
            )

        }
    ) { paddingValues ->
        val notesListState by viewModel.notesState.collectAsStateWithLifecycle()
        if (notesListState.isLoading) {
            LinearProgressIndicator()
        }
        when (val state = notesListState.content) {
            is UiState.UiContent.Data -> {
                LazyColumn(modifier = Modifier.padding(paddingValues)) {
                    items(state.value) { note ->
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(NoteRoute.Edit(note.id, -1).route())
                                }) {
                            Text(note.title)
                        }
                    }
                }
            }

            is UiState.UiContent.Error -> {
                Box(Modifier.fillMaxWidth()) {
                    Text("Error")
                }

            }
        }
    }
}