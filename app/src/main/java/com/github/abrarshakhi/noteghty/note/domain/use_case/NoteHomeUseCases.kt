package com.github.abrarshakhi.noteghty.note.domain.use_case

import com.github.abrarshakhi.noteghty.note.domain.repository.NoteRepository
import javax.inject.Inject

class NoteHomeUseCases @Inject constructor(private val repo: NoteRepository) {
    val getNotesUseCase: GetNotesUseCase = GetNotesUseCase(repo)
}