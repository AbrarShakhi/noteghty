package com.github.abrarshakhi.noteghty.note.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.github.abrarshakhi.noteghty.note.presentation.note_home.screen.NoteHomeScreen


fun NavGraphBuilder.noteNavGraph(navController: NavHostController) {
    navigation(startDestination = NoteRoute.Home.pattern, route = NoteRoute.Graph.pattern) {
        composable(route = NoteRoute.Home.pattern) {
            NoteHomeScreen(navController)
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
            // val noteId = it.arguments?.getInt("noteId") ?: -1
            // val noteColor = it.arguments?.getInt("noteColor") ?: -1
            // NoteEditScreen(noteId, noteColor)
        }
    }
}