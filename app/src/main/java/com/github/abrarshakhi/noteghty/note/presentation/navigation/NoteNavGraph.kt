package com.github.abrarshakhi.noteghty.note.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.github.abrarshakhi.noteghty.note.presentation.edit_note.NoteEditScreen
import com.github.abrarshakhi.noteghty.note.presentation.edit_note.NoteEditViewModel
import com.github.abrarshakhi.noteghty.note.presentation.note_home.NoteHomeScreen
import com.github.abrarshakhi.noteghty.note.presentation.note_home.NoteHomeViewModel


fun NavGraphBuilder.noteNavGraph(navController: NavHostController) {
    navigation(startDestination = NoteRoute.Home.pattern, route = NoteRoute.Graph.pattern) {
        composable(route = NoteRoute.Home.pattern) {
            val viewModel: NoteHomeViewModel = hiltViewModel()
            NoteHomeScreen(navController, viewModel)
        }

        composable(
            route = NoteRoute.Edit(-1, -1).pattern,
            arguments = listOf(navArgument(name = "noteId") {
                type = NavType.IntType
                defaultValue = -1
            }, navArgument(name = "noteColor") {
                type = NavType.IntType
                defaultValue = -1
            })
        ) {
             val noteId = it.arguments?.getInt("noteId") ?: -1
             val noteColor = it.arguments?.getInt("noteColor") ?: -1
            val viewModel: NoteEditViewModel = hiltViewModel()
            NoteEditScreen(navController, viewModel)
        }
    }
}