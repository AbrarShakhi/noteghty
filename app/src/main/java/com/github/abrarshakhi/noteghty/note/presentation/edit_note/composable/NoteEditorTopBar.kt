package com.github.abrarshakhi.noteghty.note.presentation.edit_note.composable

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.github.abrarshakhi.noteghty.R
import com.github.abrarshakhi.noteghty.note.presentation.edit_note.NoteEditState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditorTopBar(
    state: NoteEditState,
    onBackPress: () -> Unit,
    onPinnedChange: (Boolean) -> Unit,
) {

    CenterAlignedTopAppBar(
        navigationIcon = {
        IconButton(onClick = onBackPress) {
            Icon(
                painter = painterResource(R.drawable.outline_arrow_back_24),
                contentDescription = "Back"
            )
        }
    }, title = {}, actions = {
        // Pin
        IconToggleButton(
            checked = state.isPinned, onCheckedChange = onPinnedChange
        ) {
            Icon(
                painter = painterResource(
                    if (state.isPinned) R.drawable.outline_keep_off_24
                    else R.drawable.outline_keep_24
                ),
                contentDescription = "Pin note",
                tint = if (state.isPinned) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface
            )
        }
        // Color palette
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.outline_palette_24),
                contentDescription = "Change color"
            )
        }

        // More menu (3 dots)
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.outline_more_vert_24),
                contentDescription = "More options"
            )
        }
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = state.color.background,
        scrolledContainerColor = Color.Unspecified,
        navigationIconContentColor = Color.Unspecified,
        titleContentColor = Color.Unspecified,
        actionIconContentColor = Color.Unspecified
    )
    )
}