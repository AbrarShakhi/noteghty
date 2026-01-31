package com.github.abrarshakhi.noteghty.note.presentation.note_home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.github.abrarshakhi.noteghty.note.domain.model.Note

@Composable
fun AgendaNotesList(
    notes: List<Note>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    arrangement: Arrangement.HorizontalOrVertical,
    noteItem: @Composable (Note) -> Unit
) {
    LazyColumn(
        modifier = modifier, contentPadding = contentPadding, verticalArrangement = arrangement
    ) {
        items(
            items = notes, key = { it.id.takeIf { id -> id != -1 } ?: it.hashCode() }) {
            noteItem(it)
        }
    }
}
