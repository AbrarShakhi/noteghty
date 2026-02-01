package com.github.abrarshakhi.noteghty.note.domain.use_case

import com.github.abrarshakhi.noteghty.core.domain.utils.Outcome
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.repository.NoteRepository
import com.github.abrarshakhi.noteghty.note.domain.utils.NoteError
import javax.inject.Inject

class SaveNoteUseCase @Inject constructor(private val repo: NoteRepository) {
    operator fun invoke(note: Note): Outcome<Unit, NoteError> {
        // TODO: Make sure its not empty Note
        return repo.saveNote(note)
    }
}