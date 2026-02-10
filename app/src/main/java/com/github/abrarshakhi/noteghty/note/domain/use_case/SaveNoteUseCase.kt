package com.github.abrarshakhi.noteghty.note.domain.use_case

import com.github.abrarshakhi.noteghty.core.domain.utils.Outcome
import com.github.abrarshakhi.noteghty.core.domain.utils.errorOrThrow
import com.github.abrarshakhi.noteghty.core.domain.utils.getOrThrow
import com.github.abrarshakhi.noteghty.core.domain.utils.isErr
import com.github.abrarshakhi.noteghty.core.domain.utils.isOk
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.repository.NoteRepository
import com.github.abrarshakhi.noteghty.note.domain.utils.NoteError

class SaveNoteUseCase(private val repo: NoteRepository) {
    suspend fun async(note: Note) {
        val outcome = verityNote(note)
        if (outcome.isOk()) {
            repo.saveNoteAsync(outcome.getOrThrow { Exception() })
        }
    }

    suspend fun sync(note: Note): Outcome<Long, NoteError> {
        val outcome = verityNote(note)
        if (outcome.isErr()) {
            return Outcome.err(outcome.errorOrThrow { Exception() })
        }
        return repo.saveNote(outcome.getOrThrow { Exception() })
    }

    private fun verityNote(note: Note): Outcome<Note, NoteError> {
        var cleanedNote = note.copy(title = note.title.trim(), content = note.content.trim())
        if (cleanedNote.title.isEmpty() && cleanedNote.content.isEmpty()) {
            return Outcome.err(NoteError.EmptyNote)
        } else if (cleanedNote.title.isEmpty()) {
            cleanedNote = cleanedNote.copy(title = "Untitled")
        }
        return Outcome.ok(cleanedNote)
    }
}