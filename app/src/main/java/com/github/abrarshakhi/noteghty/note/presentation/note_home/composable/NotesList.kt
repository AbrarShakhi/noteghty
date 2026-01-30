package com.github.abrarshakhi.noteghty.note.presentation.note_home.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.presentation.note_home.ViewStyleState

@Composable
fun NotesList(
    viewStyle: ViewStyleState,
    notes: List<Note>,
    padding: PaddingValues,
    navController: NavController
) {
    when (viewStyle) {
        ViewStyleState.COZY -> CozyNotesGrid(
            notes,
            Modifier.padding(padding),
            navController
        )

        ViewStyleState.AGENDA -> AgendaNotesList(
            notes,
            Modifier.padding(padding),
            navController
        )
    }
}
