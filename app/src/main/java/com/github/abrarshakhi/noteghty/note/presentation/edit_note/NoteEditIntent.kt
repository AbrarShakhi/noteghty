package com.github.abrarshakhi.noteghty.note.presentation.edit_note

import com.github.abrarshakhi.noteghty.note.domain.model.NoteColor

sealed interface NoteEditIntent {
    object SaveForcedAndNotify : NoteEditIntent
    data class Load(val noteId: Long?) : NoteEditIntent
    data class TitleChanged(val value: String) : NoteEditIntent
    data class BodyChanged(val value: String) : NoteEditIntent
    data class ColorChanged(val value: NoteColor) : NoteEditIntent
    data class PinToggled(val value: Boolean) : NoteEditIntent
}