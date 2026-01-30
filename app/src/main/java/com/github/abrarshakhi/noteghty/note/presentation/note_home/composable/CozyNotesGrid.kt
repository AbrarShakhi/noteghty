package com.github.abrarshakhi.noteghty.note.presentation.note_home.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.presentation.navigation.NoteRoute

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CozyNotesGrid(
    notes: List<Note>,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(
            start = 12.dp,
            end = 12.dp,
            top = 12.dp,
            bottom = 200.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalItemSpacing = 12.dp
    ) {
        items(
            items = notes,
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

