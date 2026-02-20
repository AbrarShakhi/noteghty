package com.github.abrarshakhi.noteghty.note.presentation.note_home.composable

import androidx.compose.foundation.combinedClickable
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.github.abrarshakhi.noteghty.R
import com.github.abrarshakhi.noteghty.core.domain.utils.toDayMonth
import com.github.abrarshakhi.noteghty.note.domain.model.Note

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    onClick: (Note) -> Unit,
    onLongClick: (Note) -> Unit
) {
    val foregroundColor = if (note.color.isLightForeground) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onPrimary
    }

    Card(
        modifier = modifier.fillMaxWidth().combinedClickable(enabled = true, onClick = {
                onClick(note)
            }, onLongClick = {}),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = note.color.background
        )
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = foregroundColor,
            )
            Spacer(modifier.padding(top = 4.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                color = foregroundColor,
                maxLines = 5
            )
            Spacer(modifier.padding(top = 4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
            ) {
                if (note.isPinned) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_thumb_up_24),
                        contentDescription = "pinned",
                        tint = foregroundColor
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = note.updatedAt.toDayMonth(),
                    style = MaterialTheme.typography.bodySmall,
                    color = foregroundColor,
                    maxLines = 1
                )
            }
        }
    }
}