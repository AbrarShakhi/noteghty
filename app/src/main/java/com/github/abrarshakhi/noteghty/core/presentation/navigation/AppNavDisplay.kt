package com.github.abrarshakhi.noteghty.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.github.abrarshakhi.noteghty.note.presentation.navigation.NoteNavDisplay


@Composable
fun AppNavDisplay(startDestination: NavKey) {
    val appBackStack = rememberNavBackStack(startDestination)

    NavDisplay(
        backStack = appBackStack, entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ), entryProvider = entryProvider {
            entry<AppNavGraph.NoteNavKey> {
                NoteNavDisplay()
            }
        })
}