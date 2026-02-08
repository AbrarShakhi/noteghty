package com.github.abrarshakhi.noteghty.core.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppNavGraph : NavKey {

    @Serializable
    object NoteNavKey : AppNavGraph {

        @Serializable
        object Home : AppNavGraph

        @Serializable
        data class Editor(val noteId: Int) : AppNavGraph
    }
}
