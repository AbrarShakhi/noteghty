package com.github.abrarshakhi.noteghty.note.presentation.edit_note

data class NoteEditState(val isLoading: Boolean = true) {
    fun startLoading(): NoteEditState {
        return this.copy(isLoading = true)
    }
}