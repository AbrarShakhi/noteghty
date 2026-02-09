package com.github.abrarshakhi.noteghty.note.presentation.note_home

import com.github.abrarshakhi.noteghty.core.domain.listings.ListingDirection
import com.github.abrarshakhi.noteghty.note.domain.listing.NoteOrder
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.model.NoteViewStyle

data class NoteHomeState(
    val viewStyle: NoteViewStyle = NoteViewStyle.COZY,
    val noteOrder: NoteOrder = NoteOrder.Date(ListingDirection.DESCENDING),
    val isLoading: Boolean = true,
    val notes: List<Note> = emptyList()
) {
    fun setNotes(notes: List<Note>): NoteHomeState {
        return this.copy(isLoading = false, notes = notes)
    }

    fun startLoading(): NoteHomeState {
        return this.copy(isLoading = true)
    }

    fun setOrder(noteOrder: NoteOrder): NoteHomeState {
        return this.copy(noteOrder = noteOrder)
    }

    fun inverseViewStyle(): NoteHomeState {
        return this.copy(
            viewStyle = when (viewStyle) {
                NoteViewStyle.AGENDA -> NoteViewStyle.COZY
                NoteViewStyle.COZY -> NoteViewStyle.AGENDA
            }
        )
    }


}