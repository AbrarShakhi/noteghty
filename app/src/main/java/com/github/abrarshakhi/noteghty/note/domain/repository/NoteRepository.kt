package com.github.abrarshakhi.noteghty.note.domain.repository

import com.github.abrarshakhi.noteghty.core.domain.utils.Outcome
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.utils.NoteError
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<List<Note>>
    fun getNoteById(noteId: Int): Outcome<Note, NoteError>
    fun saveNote(note: Note): Outcome<Unit, NoteError>
}
