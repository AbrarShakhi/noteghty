package com.github.abrarshakhi.noteghty.note.data.mapper

import com.github.abrarshakhi.noteghty.note.data.local.database.entity.NoteEntity
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.model.NoteColor
import java.time.Instant

fun NoteEntity.toDomain(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        color = NoteColor.listOfColors[colorId],
        isPinned = isPinned,
        updatedAt = Instant.ofEpochMilli(updatedAt)
    )
}

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = id ?: 0L,
        title = title,
        content = content,
        colorId = color.id,
        isPinned = isPinned,
        updatedAt = updatedAt.toEpochMilli()
    )
}