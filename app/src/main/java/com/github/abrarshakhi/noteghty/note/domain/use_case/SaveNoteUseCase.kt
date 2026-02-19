package com.github.abrarshakhi.noteghty.note.domain.use_case

import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.repository.NoteRepository
import com.github.abrarshakhi.noteghty.note.domain.utils.NoteError
import com.github.abrarshakhi.outcome.Outcome
import com.github.abrarshakhi.outcome.errorOrThrowWith
import com.github.abrarshakhi.outcome.getOrThrowWith
import com.github.abrarshakhi.outcome.isErr
import com.github.abrarshakhi.outcome.isOk

class SaveNoteUseCase(private val repo: NoteRepository) {
    suspend fun async(note: Note) {
        val outcome = verityNote(note)
        if (outcome.isOk()) {
            repo.saveNoteAsync(outcome.getOrThrowWith { Exception() })
        }
    }

    suspend fun sync(note: Note): Outcome<Long, NoteError> {
        val outcome = verityNote(note)
        if (outcome.isErr()) {
            return Outcome.ofErr(outcome.errorOrThrowWith { Exception() })
        }
        return repo.saveNote(outcome.getOrThrowWith { Exception() })
    }

    private fun verityNote(note: Note): Outcome<Note, NoteError> {
        var cleanedNote = note.copy(title = note.title.trim(), content = note.content.trim())
        if (cleanedNote.title.isEmpty() && cleanedNote.content.isEmpty()) {
            return Outcome.ofErr(NoteError.EmptyNote)
        } else if (cleanedNote.title.isEmpty()) {
            cleanedNote = cleanedNote.copy(title = "Untitled")
        }
        return Outcome.ofOk(cleanedNote)
    }
}