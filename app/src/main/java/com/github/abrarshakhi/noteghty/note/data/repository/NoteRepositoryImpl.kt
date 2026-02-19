package com.github.abrarshakhi.noteghty.note.data.repository

import com.github.abrarshakhi.noteghty.core.di.AppModule
import com.github.abrarshakhi.noteghty.note.data.local.database.dao.NoteDao
import com.github.abrarshakhi.noteghty.note.data.mapper.toDomain
import com.github.abrarshakhi.noteghty.note.data.mapper.toEntity
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.repository.NoteRepository
import com.github.abrarshakhi.noteghty.note.domain.utils.NoteError
import com.github.abrarshakhi.outcome.Outcome
import com.github.abrarshakhi.outcome.map
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

    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes().mapLatest { list ->
            list.map { note -> note.toDomain() }
        }
    }

    override suspend fun getNoteById(noteId: Long): Outcome<Note, NoteError> {
        return try {
            Outcome.ofOk(noteDao.getNoteById(noteId)).map { it.toDomain() }
        } catch (e: CancellationException) {
            throw e
        } catch (_: Exception) {
            Outcome.ofErr(NoteError.NotFound)
        }
    }

    override suspend fun saveNote(note: Note): Outcome<Long, NoteError> {
        return try {
            Outcome.ofOk(noteDao.insertNote(note.toEntity()))
        } catch (e: CancellationException) {
            throw e
        } catch (_: Exception) {
            Outcome.ofErr(NoteError.UnableToInsert)
        }
    }

    override suspend fun saveNoteAsync(note: Note) {
        applicationScope.launch {
            noteDao.insertNote(note.toEntity())
        }
    }
}
