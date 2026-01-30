package com.github.abrarshakhi.noteghty.note.presentation.note_home.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.github.abrarshakhi.noteghty.R
import com.github.abrarshakhi.noteghty.core.ui.theme.MonokaiComments

@Composable
fun EmptyNotesList(padding: PaddingValues, onSave: () -> Unit) {
    Box(modifier = Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
        IconButton(onClick = onSave, modifier = Modifier.size(300.dp).fillMaxSize()) {
            Icon(
                painter = painterResource(R.drawable.outline_note_add_24),
                modifier = Modifier.padding(50.dp).fillMaxSize(),
                contentDescription = "add new note",
                tint = MonokaiComments
            )
        }
    }
}