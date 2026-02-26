package com.github.abrarshakhi.noteghty.note.presentation.note_home.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.github.abrarshakhi.noteghty.core.domain.listings.ListingDirection

@Composable
fun DirectionOptionRow(
    direction: ListingDirection, selected: Boolean, onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick).padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Spacer(Modifier.width(8.dp))
        Text(
            text = if (direction == ListingDirection.ASCENDING) "Ascending" else "Descending"
        )
    }
}
