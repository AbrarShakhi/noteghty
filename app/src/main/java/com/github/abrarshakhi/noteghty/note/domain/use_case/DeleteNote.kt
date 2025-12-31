package com.github.abrarshakhi.noteghty.note.domain.use_case

import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}