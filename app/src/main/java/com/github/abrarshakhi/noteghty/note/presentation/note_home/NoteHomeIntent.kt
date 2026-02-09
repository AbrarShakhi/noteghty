package com.github.abrarshakhi.noteghty.note.presentation.note_home

import com.github.abrarshakhi.noteghty.note.domain.listing.NoteOrder

sealed interface NoteHomeIntent {
    object ToggleViewStyle : NoteHomeIntent
    object LoadNotes : NoteHomeIntent

    data class SetNoteOrderingSettings(val noteOrder: NoteOrder) : NoteHomeIntent
}