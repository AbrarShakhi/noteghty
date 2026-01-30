package com.github.abrarshakhi.noteghty.note.domain.model

import java.time.Instant

data class Note(
    val id: Int = -1,

    val content: String,

    val color: Long,
    val isPinned: Boolean = false,
    val isDeleted: Boolean = false,

    val createdAt: Instant,
    val updatedAt: Instant,
) {

    /**
     * Returns a new Note instance with updated content and timestamp.
     */
    fun update(
        content: String = this.content,
        color: Long = this.color,
        isPinned: Boolean = this.isPinned,
        isDeleted: Boolean = this.isDeleted,
    ): Note {
        return copy(
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
        fun create(
            content: String,
            color: Long,
            isPinned: Boolean = false
        ): Note {
            return Note(
                content = content,
                color = color,
                isPinned = isPinned,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        }
    }
}

