package com.github.abrarshakhi.noteghty.note.domain.model

import java.time.Instant

data class Note(
    val id: Long?,
    val title: String,
    val content: String,

    val color: NoteColor,
    val isPinned: Boolean = false,

    val updatedAt: Instant,
) {
    companion object {

        /**
         * Factory method for creating a new Note.
         * Keeps construction explicit and readable.
         */
        fun newInstance(
            id: Long? = null,
            title: String,
            content: String,
            color: NoteColor,
            isPinned: Boolean = false,
        ): Note {
            return Note(
                id = id,
                title = title,
                content = content,
                color = color,
                isPinned = isPinned,
                updatedAt = Instant.now(),
            )
        }
    }
}

