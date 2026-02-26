package com.github.abrarshakhi.noteghty.note.presentation.note_home.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.abrarshakhi.noteghty.note.domain.model.Note

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CozyNotesGrid(
    notes: List<Note>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    arrangement: Arrangement.HorizontalOrVertical,
    noteItem: @Composable (Note) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = arrangement,
        verticalItemSpacing = arrangement.spacing
    ) {
        items(
            items = notes, key = { it.hashCode() }) {
            noteItem(it)
        }
    }
}

