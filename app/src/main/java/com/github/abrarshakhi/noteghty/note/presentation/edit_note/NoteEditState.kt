package com.github.abrarshakhi.noteghty.note.presentation.edit_note

import com.github.abrarshakhi.noteghty.note.domain.model.Note

data class NoteEditState(
    val isLoading: Boolean = true, val note: Note? = null
)