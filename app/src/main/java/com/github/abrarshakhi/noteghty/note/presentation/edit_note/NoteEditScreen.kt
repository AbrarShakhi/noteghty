package com.github.abrarshakhi.noteghty.note.presentation.edit_note

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.github.abrarshakhi.noteghty.core.presentation.composable.LifecycleEventsEffect
import com.github.abrarshakhi.noteghty.core.ui.theme.onPrimaryLight
import com.github.abrarshakhi.noteghty.core.ui.theme.onSurfaceLight
import com.github.abrarshakhi.noteghty.note.presentation.edit_note.composable.NoteEditorTopBar
import dev.jeziellago.compose.markdowntext.MarkdownText
import kotlinx.coroutines.flow.Flow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    noteId: Long?,
    colorId: Int?,
    state: NoteEditState,
    effect: Flow<NoteEditEffect>,
    onIntent: (NoteEditIntent) -> Unit,
    onBack: () -> Unit,
) {
    val foregroundColor = if (state.color.isLightForeground) onPrimaryLight else onSurfaceLight

    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    LaunchedEffect(noteId) {
        noteId?.let { onIntent(NoteEditIntent.Load(it)) }
    }
    LaunchedEffect(colorId) {
        colorId?.let { onIntent(NoteEditIntent.Set.Color(it)) }
    }
    LaunchedEffect(Unit) {
        effect.collect { when (it) {
            is NoteEditEffect.Error -> snackbarHostState.showSnackbar(it.message)
        }}
    }

    // Save immediately when the user backgrounds the app.
    LifecycleEventsEffect(onPause = { onIntent(NoteEditIntent.SaveNow) })

    // Navigation back — onCleared() handles the final save.
    BackHandler { onBack() }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding(),
        topBar = {
            NoteEditorTopBar(
                isPinned = state.isPinned,
                noteColor = state.color,
                listOfColors = state.listOfColors,
                isEditMode = state.isEditMode,
                onBackPress = { onBack() },
                onPinnedChange = { onIntent(NoteEditIntent.Set.TogglePinned(it)) },
                onNoteColorChange = { onIntent(NoteEditIntent.Set.Color(it.id)) },
                onToggleEditMode = { onIntent(NoteEditIntent.ToggleEditMode) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .background(state.color.background)
        ) {
            if (state.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            BasicTextField(
                value = state.title,
                onValueChange = { onIntent(NoteEditIntent.Set.Title(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textStyle = MaterialTheme.typography.headlineSmall.copy(color = foregroundColor),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                decorationBox = { inner ->
                    if (state.title.isEmpty()) {
                        Text(
                            text = "Title",
                            style = MaterialTheme.typography.headlineSmall,
                            color = foregroundColor.copy(alpha = 0.4f)
                        )
                    }
                    inner()
                }
            )

            HorizontalDivider(Modifier.padding(10.dp), DividerDefaults.Thickness, foregroundColor)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                if (state.isEditMode) {
                    BasicTextField(
                        value = state.content,
                        onValueChange = { onIntent(NoteEditIntent.Set.Content(it)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(color = foregroundColor),
                        keyboardOptions = KeyboardOptions.Default,
                        keyboardActions = KeyboardActions.Default,
                        decorationBox = { inner ->
                            if (state.content.isEmpty()) {
                                Text(
                                    text = "Start writing...",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = foregroundColor.copy(alpha = 0.4f)
                                )
                            }
                            inner()
                        }
                    )
                } else {
                    MarkdownText(
                        markdown = state.content,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp),
                        style = MaterialTheme.typography.bodyLarge.copy(color = foregroundColor),
                    )
                }
            }
        }
    }
}
