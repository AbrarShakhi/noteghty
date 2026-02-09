package com.github.abrarshakhi.noteghty.note.data.repository

import com.github.abrarshakhi.noteghty.core.domain.utils.Outcome
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.repository.NoteRepository
import com.github.abrarshakhi.noteghty.note.domain.utils.NoteError
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        TODO()
    }

    override suspend fun getNoteById(noteId: Long): Outcome<Note, NoteError> {
        TODO()
    }

    override suspend fun saveNote(note: Note): Outcome<Long, NoteError> {
        TODO()
    }
}
