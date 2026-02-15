package com.github.abrarshakhi.noteghty.note.domain.use_case

import com.github.abrarshakhi.noteghty.note.domain.model.NoteColor
import com.github.abrarshakhi.noteghty.note.domain.repository.NoteRepository

class GetNoteColorsUseCase(private val repo: NoteRepository) {
    operator fun invoke(): List<NoteColor> = repo.getNoteColors().sortedBy { it.id }
}