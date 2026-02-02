package com.github.abrarshakhi.noteghty.note.presentation.note_home.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.github.abrarshakhi.noteghty.R
import com.github.abrarshakhi.noteghty.core.domain.utils.toDayMonth
import com.github.abrarshakhi.noteghty.note.domain.model.Note

@Composable
fun NoteItem(note: Note, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = note.color.background
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium,
                color = note.color.foreground,
            )
            Spacer(modifier.padding(top = 4.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                color = note.color.foreground,
                maxLines = 5
            )
            Spacer(modifier.padding(top = 4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
            ) {
                if (note.isPinned) {
                    Icon(
                        painter = painterResource(R.drawable.outline_import_contacts_24),
                        contentDescription = "pinned",
                        tint = note.color.foreground
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = note.updatedAt.toDayMonth(),
                    style = MaterialTheme.typography.bodySmall,
                    color = note.color.foreground,
                    maxLines = 1
                )
            }
        }
    }
}