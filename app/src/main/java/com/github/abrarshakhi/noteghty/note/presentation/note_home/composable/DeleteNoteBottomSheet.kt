package com.github.abrarshakhi.noteghty.note.presentation.note_home.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.abrarshakhi.noteghty.note.domain.model.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteNoteBottomSheet(note: Note, onDelete: () -> Unit, onDismiss: () -> Unit) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(5.dp))

            Text(text = note.content, style = MaterialTheme.typography.bodyMedium, maxLines = 5)

            Spacer(Modifier.height(16.dp))

            OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = onDelete) {
                Text("Delete")
            }

            Spacer(Modifier.height(12.dp))
        }
    }
}