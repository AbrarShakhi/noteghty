package com.github.abrarshakhi.noteghty.note.data.repository

import com.github.abrarshakhi.noteghty.core.di.AppModule
import com.github.abrarshakhi.noteghty.core.domain.utils.Outcome
import com.github.abrarshakhi.noteghty.core.domain.utils.map
import com.github.abrarshakhi.noteghty.note.data.local.database.dao.NoteDao
import com.github.abrarshakhi.noteghty.note.data.mapper.toDomain
import com.github.abrarshakhi.noteghty.note.data.mapper.toRelation
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.model.NoteColor
import com.github.abrarshakhi.noteghty.note.domain.repository.NoteRepository
import com.github.abrarshakhi.noteghty.note.domain.utils.NoteError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@OptIn(ExperimentalCoroutinesApi::class)
class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    @param:AppModule.CoroutineScopeModule.ApplicationScope private val applicationScope: CoroutineScope
) : NoteRepository {

    @Volatile
    private var cachedColors: List<NoteColor> = emptyList()

    init {
        applicationScope.launch {
            cachedColors = noteDao.getNoteColors().map { it.toDomain() }
        }
    }

    override fun getNoteColors(): List<NoteColor> = cachedColors

    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotesWithColors().mapLatest { list ->
            list.map { noteWithColor -> noteWithColor.toDomain() }
        }
    }

    override suspend fun getNoteById(noteId: Long): Outcome<Note, NoteError> {
        return try {
            Outcome.ok(noteDao.getNotesWithColorsById(noteId)).map { it.toDomain() }
        } catch (e: CancellationException) {
            throw e
        } catch (_: Exception) {
            Outcome.err(NoteError.NotFound)
        }
    }

    override suspend fun saveNote(note: Note): Outcome<Long, NoteError> {
        val (note, _) = note.toRelation()
        return try {
            Outcome.ok(noteDao.insertNote(note))
        } catch (e: CancellationException) {
            throw e
        } catch (_: Exception) {
            Outcome.err(NoteError.UnableToInsert)
        }
    }

    override suspend fun saveNoteAsync(note: Note) {
        applicationScope.launch {
            val (note, _) = note.toRelation()
            noteDao.insertNote(note)
        }
    }
}
