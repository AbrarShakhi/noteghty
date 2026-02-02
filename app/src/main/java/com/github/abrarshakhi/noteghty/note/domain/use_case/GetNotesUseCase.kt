package com.github.abrarshakhi.noteghty.note.domain.use_case

import com.github.abrarshakhi.noteghty.core.domain.listings.ListingOrder
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.repository.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GetNotesUseCase @Inject constructor(private val repo: NoteRepository) {
    operator fun invoke(order: NoteOrder): Flow<List<Note>> {
        return repo.getNotes().mapLatest { notes ->
            when (order) {
                is NoteOrder.Title -> order.listingOrder.sort(notes) { it.title.lowercase() }

                is NoteOrder.Date -> order.listingOrder.sort(notes) { it.updatedAt }

                is NoteOrder.Color -> order.listingOrder.sort(notes) { it.color.hashCode() }

                is NoteOrder.Pinned -> order.listingOrder.sort(notes) { it.isPinned }
            }
        }
    }

    sealed class NoteOrder(val listingOrder: ListingOrder) {
        class Title(listingOrder: ListingOrder) : NoteOrder(listingOrder)
        class Date(listingOrder: ListingOrder) : NoteOrder(listingOrder)
        class Color(listingOrder: ListingOrder) : NoteOrder(listingOrder)
        class Pinned(listingOrder: ListingOrder) : NoteOrder(listingOrder)

        fun copy(listingOrder: ListingOrder): NoteOrder {
            return when (this) {
                is Title -> Title(listingOrder)
                is Date -> Date(listingOrder)
                is Color -> Color(listingOrder)
                is Pinned -> Pinned(listingOrder)
            }
        }


    }
}