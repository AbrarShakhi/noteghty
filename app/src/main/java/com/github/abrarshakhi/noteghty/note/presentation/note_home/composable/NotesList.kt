package com.github.abrarshakhi.noteghty.note.presentation.note_home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.model.NoteViewStyle

@Composable
fun NotesList(
    noteViewStyle: NoteViewStyle,
    notes: List<Note>,
    padding: PaddingValues,
    contentPadding: PaddingValues = PaddingValues(
        start = 12.dp, end = 12.dp, top = 12.dp, bottom = 200.dp
    ),
    arrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(12.dp),
    noteItem: @Composable (Note) -> Unit
) {
    when (noteViewStyle) {
        NoteViewStyle.COZY -> CozyNotesGrid(
            notes = notes,
            modifier = Modifier.padding(padding),
            contentPadding = contentPadding,
            arrangement = arrangement,
            noteItem = noteItem
        )

        NoteViewStyle.AGENDA -> AgendaNotesList(
            notes = notes,
            modifier = Modifier.padding(padding),
            contentPadding = contentPadding,
            arrangement = arrangement,
            noteItem = noteItem
        )
    }
}
