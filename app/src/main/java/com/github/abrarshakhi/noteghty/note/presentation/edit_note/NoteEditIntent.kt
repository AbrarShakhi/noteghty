package com.github.abrarshakhi.noteghty.note.presentation.edit_note

sealed interface NoteEditIntent {
    data class Load(val noteId: Long?) : NoteEditIntent
    object SaveAsynchronous : NoteEditIntent

    object TogglePinned : NoteEditIntent
    sealed interface ChangeTitleOrContent : NoteEditIntent {
        data class Title(val newTitle: String) : ChangeTitleOrContent
        data class Content(val newContent: String) : ChangeTitleOrContent
    }

}

