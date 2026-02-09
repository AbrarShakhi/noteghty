package com.github.abrarshakhi.noteghty.note.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.github.abrarshakhi.noteghty.core.presentation.navigation.AppNavGraph
import com.github.abrarshakhi.noteghty.note.presentation.edit_note.NoteEditScreen
import com.github.abrarshakhi.noteghty.note.presentation.edit_note.NoteEditViewModel
import com.github.abrarshakhi.noteghty.note.presentation.note_home.NoteHomeScreen
import com.github.abrarshakhi.noteghty.note.presentation.note_home.NoteHomeViewModel


@Composable
fun NoteNavDisplay(startDestination: NavKey = AppNavGraph.NoteNavKey.Home) {
    val noteBackStack = rememberNavBackStack(startDestination)

    NavDisplay(
        backStack = noteBackStack, entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ), entryProvider = entryProvider {
            entry<AppNavGraph.NoteNavKey.Home> {
                val viewModel: NoteHomeViewModel = hiltViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                NoteHomeScreen(
                    state = state,
                    effect = viewModel.effect,
                    onIntent = viewModel::onIntent,
                    onEditNoteNavigation = { noteBackStack.add(AppNavGraph.NoteNavKey.Editor(it)) })
            }
            entry<AppNavGraph.NoteNavKey.Editor> { it ->
                val viewModel: NoteEditViewModel = hiltViewModel(key = it.noteId.toString())
                val state by viewModel.state.collectAsStateWithLifecycle()
                NoteEditScreen(
                    noteId = it.noteId,
                    state = state,
                    effect = viewModel.effect,
                    onIntent = viewModel::onIntent,
                    onBack = { noteBackStack.removeLastOrNull() })
            }
        })
}

