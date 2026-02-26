package com.github.abrarshakhi.noteghty.note.domain.use_case

import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.repository.NoteRepository
import com.github.abrarshakhi.noteghty.note.domain.utils.NoteError
import com.github.abrarshakhi.outcome.Outcome

class GetNoteByIdUseCase(private val repo: NoteRepository) {
    suspend operator fun invoke(noteId: Long): Outcome<Note, NoteError> = repo.getNoteById(noteId)

}