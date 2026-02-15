package com.github.abrarshakhi.noteghty.note.domain.repository

import com.github.abrarshakhi.noteghty.core.domain.utils.Outcome
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.model.NoteColor
import com.github.abrarshakhi.noteghty.note.domain.utils.NoteError
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<List<Note>>
    fun getNoteColors(): List<NoteColor>
    suspend fun getNoteById(noteId: Long): Outcome<Note, NoteError>
    suspend fun saveNote(note: Note): Outcome<Long, NoteError>
    suspend fun saveNoteAsync(note: Note)
}
