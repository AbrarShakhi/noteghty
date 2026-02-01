package com.github.abrarshakhi.noteghty.note.domain.model

import java.time.Instant

data class Note(
    val id: Int?,
    val title: String,
    val content: String,

    val color: NoteColor,
    val isPinned: Boolean = false,
    val isDeleted: Boolean = false,

    val createdAt: Instant,
    val updatedAt: Instant,
) {

    /**
     * Returns a new Note instance with updated content and timestamp.
     */
    fun update(
        title: String = this.title,
        content: String = this.content,
        color: NoteColor = this.color,
        isPinned: Boolean = this.isPinned,
        isDeleted: Boolean = this.isDeleted,
    ): Note {
        return copy(
            title = title,
            content = content,
            color = color,
            isPinned = isPinned,
            isDeleted = isDeleted,
            updatedAt = Instant.now()
        )
    }

    companion object {

        /**
         * Factory method for creating a new Note.
         * Keeps construction explicit and readable.
         */
        fun create(title: String, content: String, color: NoteColor, isPinned: Boolean = false
        ): Note {
            return Note(
                id = null,
                title = title,
                content = content,
                color = color,
                isPinned = isPinned,
                createdAt = Instant.now(),
                updatedAt = Instant.now(),
            )
        }
    }
}

