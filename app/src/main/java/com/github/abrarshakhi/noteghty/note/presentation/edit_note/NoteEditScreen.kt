package com.github.abrarshakhi.noteghty.note.presentation.edit_note

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.github.abrarshakhi.noteghty.note.presentation.edit_note.composable.NoteEditorTopBar
import kotlinx.coroutines.flow.Flow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    noteId: Long?,
    colorId: Long?,
    state: NoteEditState,
    effect: Flow<NoteEditEffect>,
    onIntent: (NoteEditIntent) -> Unit,
    onBack: () -> Unit,
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current

    val scrollState = rememberScrollState()
    rememberCoroutineScope()


    // Load note if needed
    LaunchedEffect(noteId) {
        noteId?.let {
            onIntent(NoteEditIntent.Load(it))
        }
    }
    LaunchedEffect(colorId) { colorId?.let { onIntent(NoteEditIntent.Set.Color(it)) } }

    LaunchedEffect(Unit) {
        effect.collect { it ->
            when (it) {
                is NoteEditEffect.Error -> snackbarHostState.showSnackbar(it.message)
                is NoteEditEffect.SavedSuccessfulAndReadyToGoBack -> onBack()
            }
        }
    }

    BackHandler {
        onIntent(NoteEditIntent.SaveAsynchronous)
        onBack()
    }


    Scaffold(
        topBar = {
        NoteEditorTopBar(state = state, onBackPress = {
            onIntent(NoteEditIntent.SaveAsynchronous)
            onBack()
        }, onPinnedChange = { onIntent(NoteEditIntent.Set.TogglePinned(it)) })
    },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
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
                textStyle = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                decorationBox = { inner ->
                    if (state.title.isEmpty()) {
                        Text(
                            text = "Title",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                        )
                    }
                    inner()
                })

            // Content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f) // fills remaining space
                    .padding(horizontal = 8.dp)
            ) {
                BasicTextField(
                    value = state.content,
                    onValueChange = { onIntent(NoteEditIntent.Set.Content(it)) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    keyboardOptions = KeyboardOptions.Default,
                    keyboardActions = KeyboardActions.Default,
                    decorationBox = { inner ->
                        if (state.content.isEmpty()) {
                            Text(
                                text = "Start writing...",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                            )
                        }
                        inner()
                    })
            }
        }
    }
}
