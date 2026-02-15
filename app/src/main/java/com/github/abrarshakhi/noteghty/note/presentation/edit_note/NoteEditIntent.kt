package com.github.abrarshakhi.noteghty.note.presentation.edit_note

sealed interface NoteEditIntent {
    data class Load(val noteId: Long?) : NoteEditIntent
    object SaveAsynchronous : NoteEditIntent

    sealed interface Set : NoteEditIntent {
        data class Title(val newTitle: String) : Set
        data class Content(val newContent: String) : Set
        data class TogglePinned(val pinned: Boolean) : Set
        data class Color(val colorId: Long): Set
    }

}

