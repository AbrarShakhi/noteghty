package com.github.abrarshakhi.noteghty.note.domain.utils

import com.github.abrarshakhi.noteghty.core.domain.utils.Error


sealed interface NoteError : Error {
    object NotFound : NoteError
}