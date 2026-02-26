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
import com.github.abrarshakhi.noteghty.note.presentation.note_home.NoteHomeIntent
import com.github.abrarshakhi.noteghty.note.presentation.note_home.NoteHomeScreen
import com.github.abrarshakhi.noteghty.note.presentation.note_home.NoteHomeViewModel


@Composable
fun NoteNavDisplay(startDestination: NavKey = AppNavGraph.NoteNavKey.Home) {
    val noteBackStack = rememberNavBackStack(startDestination)

    val homeViewModel: NoteHomeViewModel = hiltViewModel()
    val homeState by homeViewModel.state.collectAsStateWithLifecycle()

    NavDisplay(
        backStack = noteBackStack, entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ), entryProvider = entryProvider {
            entry<AppNavGraph.NoteNavKey.Home> {
                NoteHomeScreen(
                    state = homeState,
                    effect = homeViewModel.effect,
                    onIntent = homeViewModel::onIntent,
                    onEditNoteNavigation = {
                        noteBackStack.add(
                            AppNavGraph.NoteNavKey.Editor(
                                it?.id,
                                it?.color?.id
                            )
                        )
                    })
            }
            entry<AppNavGraph.NoteNavKey.Editor> { (noteId, colorId) ->
                val editViewModel: NoteEditViewModel = hiltViewModel(key = noteId.toString() + colorId.toString())
                val editState by editViewModel.state.collectAsStateWithLifecycle()
                NoteEditScreen(
                    noteId = noteId,
                    colorId = colorId,
                    state = editState,
                    effect = editViewModel.effect,
                    onIntent = editViewModel::onIntent,
                    onBack = {
                        homeViewModel.onIntent(NoteHomeIntent.LoadNotes)
                        noteBackStack.removeLastOrNull()
                    },
                )
            }
        })
}

