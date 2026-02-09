package com.github.abrarshakhi.noteghty.note.presentation.edit_note

sealed interface NoteEditEffect {
    object SavedSuccessfulAndReadyToGoBack : NoteEditEffect
    data class Error(val message: String) : NoteEditEffect
}