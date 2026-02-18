package com.github.abrarshakhi.noteghty.note.presentation.edit_note.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
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
import com.github.abrarshakhi.noteghty.note.domain.model.NoteColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditorTopBar(
    isPinned: Boolean,
    color: NoteColor,
    listOfColors: List<NoteColor>,
    onBackPress: () -> Unit,
    onPinnedChange: (Boolean) -> Unit,
    onColorPick: (Int, Long) -> Unit
) {
    var isThemeFocus by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        navigationIcon = {
        IconButton(
            onClick = if (isThemeFocus) {
            { isThemeFocus = false }
        } else onBackPress) {
            Icon(
                painter = painterResource(R.drawable.outline_arrow_back_24),
                contentDescription = "Back"
            )
        }
    }, title = {}, actions = {
        if (isThemeFocus) {
            ThemeActions(
                listOfColors = listOfColors,
                selectedColorId = color.id,
                onColorPick = onColorPick
            )
        } else {
            DefaultActions(
                isPinned = isPinned,
                onPinnedChange = onPinnedChange,
                onThemeChange = { isThemeFocus = true })
        }
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = color.background,
        scrolledContainerColor = Color.Unspecified,
        navigationIconContentColor = Color.Unspecified,
        titleContentColor = Color.Unspecified,
        actionIconContentColor = Color.Unspecified
    )
    )
}

@Composable
fun ThemeActions(
    listOfColors: List<NoteColor>, selectedColorId: Long, onColorPick: (Int, Long) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(start = 50.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        itemsIndexed(items = listOfColors, key = { _, item -> item.id }) { index, noteColor ->

            val isSelected = noteColor.id == selectedColorId

            Box(
                modifier = Modifier.size(32.dp).clip(CircleShape).background(noteColor.background)
                    .border(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    ).clickable {
                        onColorPick(index, noteColor.id)
                    })
        }
    }
}

@Composable
fun DefaultActions(
    isPinned: Boolean, onPinnedChange: (Boolean) -> Unit, onThemeChange: () -> Unit
) {
    IconToggleButton(
        checked = isPinned, onCheckedChange = onPinnedChange
    ) {
        Icon(
            painter = painterResource(
                if (isPinned) R.drawable.outline_keep_off_24
                else R.drawable.outline_keep_24
            ),
            contentDescription = "Pin note",
            tint = if (isPinned) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface
        )
    }
    // Color palette
    IconButton(onClick = onThemeChange) {
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
}