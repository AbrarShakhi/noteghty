package com.github.abrarshakhi.noteghty.note.presentation.home

import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.utils.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent()
    object RestoreNote : NotesEvent()
    object ToggleOrderSection : NotesEvent()
}
