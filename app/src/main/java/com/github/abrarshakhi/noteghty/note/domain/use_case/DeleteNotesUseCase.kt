package com.github.abrarshakhi.noteghty.note.domain.use_case

import com.github.abrarshakhi.noteghty.note.domain.repository.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteNotesUseCase(private val repo: NoteRepository) {
    suspend operator fun invoke(noteId: Long) = repo.deleteNote(noteId)
}