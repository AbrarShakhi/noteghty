package com.github.abrarshakhi.noteghty.note.presentation.navigation

import com.github.abrarshakhi.noteghty.core.presentation.navigation.AppRoute

sealed class NoteRoute(pattern: String) : AppRoute(pattern) {

    object Graph : NoteRoute("note")

    object Home : NoteRoute("note_home")

    data class Edit(val noteId: Int) : NoteRoute(
        "note_edit?noteId={noteId}"
    ) {
        override fun route(): String = "note_edit?noteId=$noteId"
    }
}
