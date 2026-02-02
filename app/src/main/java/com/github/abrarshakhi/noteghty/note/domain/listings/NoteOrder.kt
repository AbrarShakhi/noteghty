package com.github.abrarshakhi.noteghty.note.domain.listings

import com.github.abrarshakhi.noteghty.core.domain.listings.ListingDirection
import com.github.abrarshakhi.noteghty.core.domain.listings.toggle
import com.github.abrarshakhi.noteghty.note.domain.model.Note

sealed class NoteOrder(val listingDirection: ListingDirection) {

    abstract fun order(notes: Iterable<Note>): List<Note>

    protected fun <R : Comparable<R>> sort(
        notes: Iterable<Note>, selector: (Note) -> R
    ): List<Note> = when (listingDirection) {
        ListingDirection.ASCENDING -> notes.sortedBy(selector)
        ListingDirection.DESCENDING -> notes.sortedByDescending(selector)
    }

    class Title(listingDirection: ListingDirection) : NoteOrder(listingDirection) {
        override fun order(notes: Iterable<Note>) = sort(notes) { it.title }
    }

    class Date(listingDirection: ListingDirection) : NoteOrder(listingDirection) {
        override fun order(notes: Iterable<Note>) = sort(notes) { it.updatedAt }
    }

    class Color(listingDirection: ListingDirection) : NoteOrder(listingDirection) {
        override fun order(notes: Iterable<Note>) = sort(notes) { it.color.background.value }
    }

    class Pinned(listingDirection: ListingDirection) : NoteOrder(listingDirection) {
        override fun order(notes: Iterable<Note>) = sort(notes) { it.isPinned }
    }
}

fun NoteOrder.toggleDirection(): NoteOrder = when (this) {
    is NoteOrder.Title -> NoteOrder.Title(listingDirection.toggle())
    is NoteOrder.Date -> NoteOrder.Date(listingDirection.toggle())
    is NoteOrder.Color -> NoteOrder.Color(listingDirection.toggle())
    is NoteOrder.Pinned -> NoteOrder.Pinned(listingDirection.toggle())
}


