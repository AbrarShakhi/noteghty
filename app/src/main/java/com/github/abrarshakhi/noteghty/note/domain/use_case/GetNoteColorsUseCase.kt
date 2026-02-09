package com.github.abrarshakhi.noteghty.note.domain.use_case

import com.github.abrarshakhi.noteghty.note.domain.model.NoteColor
import com.github.abrarshakhi.noteghty.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNoteColorsUseCase(private val repo: NoteRepository) {
    operator fun invoke(): Flow<List<NoteColor>> = repo.getNoteColors()
}