package com.github.abrarshakhi.noteghty.note.domain.use_case

import com.github.abrarshakhi.noteghty.note.domain.listings.NoteOrder
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.repository.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GetNotesUseCase @Inject constructor(private val repo: NoteRepository) {
    operator fun invoke(order: NoteOrder): Flow<List<Note>> = repo.getNotes().mapLatest {
        order.order(it)
    }
}