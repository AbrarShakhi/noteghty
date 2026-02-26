package com.github.abrarshakhi.noteghty.note.presentation.note_home

import com.github.abrarshakhi.noteghty.note.domain.listing.NoteOrder
import com.github.abrarshakhi.noteghty.note.domain.model.Note

sealed interface NoteHomeIntent {
    object ToggleViewStyle : NoteHomeIntent
    object LoadNotes : NoteHomeIntent
    data class DeleteNote(val note: Note): NoteHomeIntent
    data class SetNoteOrderingSettings(val noteOrder: NoteOrder) : NoteHomeIntent
}