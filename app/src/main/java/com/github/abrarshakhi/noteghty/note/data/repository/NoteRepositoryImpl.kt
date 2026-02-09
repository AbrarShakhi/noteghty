package com.github.abrarshakhi.noteghty.note.data.repository

import com.github.abrarshakhi.noteghty.core.domain.utils.Outcome
import com.github.abrarshakhi.noteghty.core.domain.utils.map
import com.github.abrarshakhi.noteghty.note.data.local.database.dao.NoteDao
import com.github.abrarshakhi.noteghty.note.data.mapper.toDomain
import com.github.abrarshakhi.noteghty.note.data.mapper.toRelation
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.model.NoteColor
import com.github.abrarshakhi.noteghty.note.domain.repository.NoteRepository
import com.github.abrarshakhi.noteghty.note.domain.utils.NoteError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao) : NoteRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotesWithColors().mapLatest { list ->
            list.map { noteWithColor -> noteWithColor.toDomain() }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getNoteColors(): Flow<List<NoteColor>> {
        return noteDao.getNoteColors().mapLatest { list ->
            list.map { noteColor -> noteColor.toDomain() }
        }
    }

    override suspend fun getNoteById(noteId: Long): Outcome<Note, NoteError> {
        return try {
            Outcome.ok(noteDao.getNotesWithColorsById(noteId)).map { it.toDomain() }
        } catch (e: Exception) {
            Outcome.err(NoteError.NotFound)
        }
    }

    override suspend fun saveNote(note: Note): Outcome<Long, NoteError> {
        val (note, _) = note.toRelation()
        return try {
            Outcome.ok(noteDao.insertNote(note))
        } catch (e: Exception) {
            Outcome.err(NoteError.UnableToInsert)
        }
    }
}
