package com.github.abrarshakhi.noteghty.note.domain.use_case

import com.github.abrarshakhi.noteghty.note.domain.repository.NoteRepository
import javax.inject.Inject

class NoteEditUseCases @Inject constructor(private val repo: NoteRepository) {
    val getNoteByIdUseCase: GetNoteByIdUseCase = GetNoteByIdUseCase(repo)
    val getNoteColorsUseCase: GetNoteColorsUseCase = GetNoteColorsUseCase(repo)
    val saveNoteUseCase: SaveNoteUseCase = SaveNoteUseCase(repo)
}