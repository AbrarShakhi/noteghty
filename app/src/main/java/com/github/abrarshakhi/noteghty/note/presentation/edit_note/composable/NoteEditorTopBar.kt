package com.github.abrarshakhi.noteghty.note.presentation.edit_note.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.github.abrarshakhi.noteghty.R
import com.github.abrarshakhi.noteghty.core.ui.theme.onPrimaryDark
import com.github.abrarshakhi.noteghty.core.ui.theme.onPrimaryLight
import com.github.abrarshakhi.noteghty.note.domain.model.NoteColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditorTopBar(
    isPinned: Boolean,
    noteColor: NoteColor,
    listOfColors: List<NoteColor>,
    isEditMode: Boolean,
    onBackPress: () -> Unit,
    onPinnedChange: (Boolean) -> Unit,
    onNoteColorChange: (NoteColor) -> Unit,
    onToggleEditMode: () -> Unit,
) {
    val foregroundColor = if (noteColor.isLightForeground) onPrimaryLight else onPrimaryDark

    var showTheme by remember { mutableStateOf(false) }
    CenterAlignedTopAppBar(
        navigationIcon = {
            IconButton(
                onClick = if (showTheme) {
                    { showTheme = false }
                } else onBackPress
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_arrow_back_24),
                    contentDescription = "Back",
                    tint = foregroundColor
                )
            }
        },
        title = {},
        actions = {
            if (showTheme) {
                ThemeActions(
                    listOfColors = listOfColors,
                    selectedColorId = noteColor.id,
                    onNoteColorChange = onNoteColorChange,
                    isLightForeground = noteColor.isLightForeground
                )
            } else {
                DefaultActions(
                    isPinned = isPinned,
                    isEditMode = isEditMode,
                    onPinnedChange = onPinnedChange,
                    onShowThemeChange = { showTheme = true },
                    onToggleEditMode = onToggleEditMode,
                    foregroundColor = foregroundColor
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = noteColor.background,
            scrolledContainerColor = Color.Unspecified,
            navigationIconContentColor = Color.Unspecified,
            titleContentColor = Color.Unspecified,
            actionIconContentColor = Color.Unspecified
        )
    )
}

@Composable
fun ThemeActions(
    listOfColors: List<NoteColor>,
    selectedColorId: Int,
    onNoteColorChange: (NoteColor) -> Unit,
    isLightForeground: Boolean
) {
    LazyRow(modifier = Modifier.padding(start = 50.dp)) {
        items(listOfColors, key = { it.id }) { color ->
            val isSelected = color.id == selectedColorId
            Box(
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(color.background)
                    .border(
                        width = if (isSelected) 3.dp else 0.dp,
                        color = if (isSelected) if (isLightForeground) onPrimaryLight else onPrimaryDark
                        else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable { onNoteColorChange(color) }
            )
        }
    }
}

@Composable
fun DefaultActions(
    isPinned: Boolean,
    isEditMode: Boolean,
    onPinnedChange: (Boolean) -> Unit,
    onShowThemeChange: () -> Unit,
    onToggleEditMode: () -> Unit,
    foregroundColor: Color
) {
    IconButton(onClick = onToggleEditMode) {
        Icon(
            imageVector = if (isEditMode) Icons.Outlined.Visibility else Icons.Outlined.Edit,
            contentDescription = if (isEditMode) "Preview markdown" else "Edit markdown",
            tint = foregroundColor
        )
    }
    IconToggleButton(
        checked = isPinned, onCheckedChange = onPinnedChange
    ) {
        Icon(
            painter = painterResource(
                if (isPinned) R.drawable.outline_keep_off_24
                else R.drawable.outline_keep_24
            ),
            contentDescription = "Pin note",
            tint = foregroundColor
        )
    }
    IconButton(onClick = onShowThemeChange) {
        Icon(
            painter = painterResource(R.drawable.outline_palette_24),
            contentDescription = "Change color",
            tint = foregroundColor
        )
    }
    IconButton(onClick = {}) {
        Icon(
            painter = painterResource(R.drawable.outline_more_vert_24),
            contentDescription = "More options",
            tint = foregroundColor
        )
    }
}
