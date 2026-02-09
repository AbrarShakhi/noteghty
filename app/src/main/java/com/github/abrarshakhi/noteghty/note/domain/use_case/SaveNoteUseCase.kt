package com.github.abrarshakhi.noteghty.note.domain.use_case

import com.github.abrarshakhi.noteghty.core.domain.utils.Outcome
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.repository.NoteRepository
import com.github.abrarshakhi.noteghty.note.domain.utils.NoteError

class SaveNoteUseCase(private val repo: NoteRepository) {
    suspend operator fun invoke(note: Note): Outcome<Long, NoteError> {
        var cleanedNote = note.copy(title = note.title.trim(), content = note.content.trim())
        if (cleanedNote.title.isEmpty() && cleanedNote.content.isEmpty()) {
            return Outcome.err(NoteError.EmptyNote)
        } else if (cleanedNote.title.isEmpty()) {
            cleanedNote = cleanedNote.copy(title = "Untitled")
        }
        return repo.saveNote(cleanedNote)
    }
}