package com.github.abrarshakhi.noteghty.note.domain.use_case

import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.repository.NoteRepository
import com.github.abrarshakhi.noteghty.note.domain.utils.NoteError
import com.github.abrarshakhi.outcome.Outcome
import com.github.abrarshakhi.outcome.errorOrNull
import com.github.abrarshakhi.outcome.getOrNull
import com.github.abrarshakhi.outcome.isErr
import com.github.abrarshakhi.outcome.isOk
import com.github.abrarshakhi.outcome.map

class SaveNoteUseCase(private val repo: NoteRepository) {
    suspend fun async(note: Note) {
        val outcome = verityNote(note)
        if (outcome.isOk()) {
            return repo.saveNoteAsync(outcome.getOrNull() ?: return)
        }
        val error = outcome.errorOrNull() ?: return
        if (error is NoteError.EmptyNote) {
            repo.deleteNote(note.id ?: return)
        }
    }

    suspend fun sync(note: Note): Outcome<Long, NoteError> {
        val outcome = verityNote(note)
        if (outcome.isErr()) {
            return outcome.map { it.id ?: -1 }
        }
        return repo.saveNote(outcome.getOrNull() ?: return Outcome.ofErr(NoteError.UnableToInsert))
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