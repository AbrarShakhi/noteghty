package com.github.abrarshakhi.noteghty.note.presentation.note_home

sealed interface NoteHomeEffect {
    data class Error(val message: String) : NoteHomeEffect
}