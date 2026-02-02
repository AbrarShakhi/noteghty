package com.github.abrarshakhi.noteghty.note.domain.listings

import com.github.abrarshakhi.noteghty.core.domain.listings.ListingDirection
import com.github.abrarshakhi.noteghty.core.domain.listings.toggle
import com.github.abrarshakhi.noteghty.note.domain.model.Note

sealed class NoteOrder(val direction: ListingDirection) {

    abstract fun order(notes: Iterable<Note>): List<Note>

    protected fun <R : Comparable<R>> sort(
        notes: Iterable<Note>, selector: (Note) -> R
    ): List<Note> = when (direction) {
        ListingDirection.ASCENDING -> notes.sortedBy(selector)
        ListingDirection.DESCENDING -> notes.sortedByDescending(selector)
    }

    class Title(direction: ListingDirection) : NoteOrder(direction) {
        override fun order(notes: Iterable<Note>) = sort(notes) { it.title }
    }

    class Date(direction: ListingDirection) : NoteOrder(direction) {
        override fun order(notes: Iterable<Note>) = sort(notes) { it.updatedAt }
    }

    class Color(direction: ListingDirection) : NoteOrder(direction) {
        override fun order(notes: Iterable<Note>) = sort(notes) { it.color.background.value }
    }

    class Pinned(direction: ListingDirection) : NoteOrder(direction) {
        override fun order(notes: Iterable<Note>) = sort(notes) { it.isPinned }
    }
}

fun NoteOrder.toggleDirection(): NoteOrder = when (this) {
    is NoteOrder.Title -> NoteOrder.Title(direction.toggle())
    is NoteOrder.Date -> NoteOrder.Date(direction.toggle())
    is NoteOrder.Color -> NoteOrder.Color(direction.toggle())
    is NoteOrder.Pinned -> NoteOrder.Pinned(direction.toggle())
}

fun NoteOrder.copyDirection(direction: ListingDirection): NoteOrder {
    return when (this) {
        is NoteOrder.Date -> NoteOrder.Date(direction)
        is NoteOrder.Title -> NoteOrder.Title(direction)
        is NoteOrder.Color -> NoteOrder.Color(direction)
        is NoteOrder.Pinned -> NoteOrder.Pinned(direction)
    }
}


