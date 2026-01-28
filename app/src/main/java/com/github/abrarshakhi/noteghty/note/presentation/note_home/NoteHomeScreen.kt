package com.github.abrarshakhi.noteghty.note.presentation.note_home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.github.abrarshakhi.noteghty.R
import com.github.abrarshakhi.noteghty.core.ui.state.UiState
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.presentation.navigation.NoteRoute

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
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(R.drawable.outline_swap_vert_24),
                            contentDescription = "Sort"
                        )
                    }

                    IconButton(onClick = { viewModel.toggleViewStyle() }) {
                        val viewStyle by viewModel.viewStyle.collectAsStateWithLifecycle()
                        Icon(
                            painter = painterResource(
                                when (viewStyle) {
                                    ViewStyleState.COZY -> R.drawable.outline_view_agenda_24
                                    ViewStyleState.AGENDA -> R.drawable.outline_view_cozy_24
                                }
                            ),
                            contentDescription = "Rearrange"
                        )
                    }
                }
            )

        }
    ) { padding ->
        val notesListState by viewModel.notesState.collectAsStateWithLifecycle()

        if (notesListState.isLoading) {
            LinearProgressIndicator(modifier = Modifier.padding(padding).fillMaxWidth())
        }

        when (val state = notesListState.content) {
            is UiState.UiContent.Data -> {
                // LazyVerticalStaggeredGrid google keep style
                LazyColumn(
                    modifier = Modifier.padding(padding),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 200.dp,
                        top = 16.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = state.value,
                        key = { it.id.takeIf { id -> id != -1 } ?: it.hashCode() }
                    ) { note ->
                        NoteItem(
                            note = note,
                            onClick = {
                                navController.navigate(
                                    NoteRoute.Edit(note.id, -1).route()
                                )
                            }
                        )
                    }
                }

            }

            is UiState.UiContent.Error -> {
                Box(Modifier.fillMaxWidth().padding(padding)) {
                    Text("Error")
                }

            }
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(note.color)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                if (note.isPinned) {
                    Icon(
                        imageVector = Icons.Default.PushPin,
                        contentDescription = "Pinned",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
