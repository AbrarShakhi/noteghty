package com.github.abrarshakhi.noteghty.note.domain.use_case

import com.github.abrarshakhi.noteghty.core.domain.utils.Outcome
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.repository.NoteRepository
import com.github.abrarshakhi.noteghty.note.domain.utils.NoteError

class GetNoteByIdUseCase(private val repo: NoteRepository) {
    suspend operator fun invoke(noteId: Long): Outcome<Note, NoteError> = repo.getNoteById(noteId)

}