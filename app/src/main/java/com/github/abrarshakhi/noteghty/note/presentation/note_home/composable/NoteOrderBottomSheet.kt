package com.github.abrarshakhi.noteghty.note.presentation.note_home.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.abrarshakhi.noteghty.core.domain.listings.ListingDirection
import com.github.abrarshakhi.noteghty.note.domain.listings.NoteOrder
import com.github.abrarshakhi.noteghty.note.domain.listings.copyDirection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteOrderBottomSheet(
    currentOrder: NoteOrder, onDismiss: () -> Unit, onSave: (NoteOrder) -> Unit
) {
    var selectedOrder by remember { mutableStateOf(currentOrder) }

    ModalBottomSheet(
        onDismissRequest = onDismiss, dragHandle = { BottomSheetDefaults.DragHandle() }) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(20.dp)
        ) {

            Text(text = "Sort notes", style = MaterialTheme.typography.titleLarge)

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Order by", style = MaterialTheme.typography.labelLarge
            )

            Spacer(Modifier.height(8.dp))

            OrderOptionRow(
                title = "Date", selected = selectedOrder is NoteOrder.Date, onClick = {
                    selectedOrder = NoteOrder.Date(selectedOrder.direction)
                })

            OrderOptionRow(
                title = "Title", selected = selectedOrder is NoteOrder.Title, onClick = {
                    selectedOrder = NoteOrder.Title(selectedOrder.direction)
                })

            OrderOptionRow(
                title = "Color", selected = selectedOrder is NoteOrder.Color, onClick = {
                    selectedOrder = NoteOrder.Color(selectedOrder.direction)
                })

            OrderOptionRow(
                title = "Pinned", selected = selectedOrder is NoteOrder.Pinned, onClick = {
                    selectedOrder = NoteOrder.Pinned(selectedOrder.direction)
                })

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Direction", style = MaterialTheme.typography.labelLarge
            )

            Spacer(Modifier.height(8.dp))

            DirectionOptionRow(
                direction = ListingDirection.ASCENDING,
                selected = selectedOrder.direction == ListingDirection.ASCENDING
            ) {
                selectedOrder = selectedOrder.copyDirection(ListingDirection.ASCENDING)
            }

            DirectionOptionRow(
                direction = ListingDirection.DESCENDING,
                selected = selectedOrder.direction == ListingDirection.DESCENDING
            ) {
                selectedOrder = selectedOrder.copyDirection(ListingDirection.DESCENDING)
            }

            Spacer(Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(), onClick = { onSave(selectedOrder) }) {
                Text("Save")
            }

            Spacer(Modifier.height(12.dp))
        }
    }
}
