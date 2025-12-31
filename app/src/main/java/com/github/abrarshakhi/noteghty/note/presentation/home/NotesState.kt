package com.github.abrarshakhi.noteghty.note.presentation.home

import com.github.abrarshakhi.noteghty.note.domain.utils.NoteOrder
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.utils.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
