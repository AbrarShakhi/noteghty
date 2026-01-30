package com.github.abrarshakhi.noteghty.note.presentation.note_home.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.presentation.navigation.NoteRoute

@Composable
fun AgendaNotesList(notes: List<Note>, modifier: Modifier = Modifier, navController: NavController) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = 12.dp,
            end = 12.dp,
            bottom = 200.dp,
            top = 12.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
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
