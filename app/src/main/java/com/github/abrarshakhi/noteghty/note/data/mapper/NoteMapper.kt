package com.github.abrarshakhi.noteghty.note.data.mapper

import androidx.compose.ui.graphics.Color
import com.github.abrarshakhi.noteghty.note.data.local.database.entity.NoteColorEntity
import com.github.abrarshakhi.noteghty.note.data.local.database.entity.NoteEntity
import com.github.abrarshakhi.noteghty.note.data.local.database.relation.NoteWithColorRelation
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.model.NoteColor
import java.time.Instant

fun NoteWithColorRelation.toDomain(): Note {
    return Note(
        id = note.id,
        title = note.title,
        content = note.content,
        color = NoteColor(
            id = color.id,
            primary = Color(color.primaryColor),
            background = Color(color.backgroundColor),
            foreground = Color(color.foregroundColor)
        ),
        isPinned = note.isPinned,
        isDeleted = note.isDeleted,
        updatedAt = Instant.ofEpochMilli(note.updatedAt)
    )
}

fun NoteColorEntity.toDomain(): NoteColor {
    return NoteColor(
        id = id,
        primary = Color(primaryColor),
        background = Color(backgroundColor),
        foreground = Color(foregroundColor)
    )
}

fun NoteColor.toEntity(): NoteColorEntity {
    return NoteColorEntity(
        id = id,
        primaryColor = primary.value.toLong(),
        backgroundColor = background.value.toLong(),
        foregroundColor = foreground.value.toLong()
    )
}

fun Note.toEntity(noteColorEntity: NoteColorEntity): NoteEntity {
    return NoteEntity(
        id = id ?: 0L,
        title = title,
        content = content,
        colorId = noteColorEntity.id,
        isPinned = isPinned,
        isDeleted = isDeleted,
        updatedAt = updatedAt.toEpochMilli()
    )
}

fun Note.toRelation(): NoteWithColorRelation {
    val colorEntity = color.toEntity()
    return NoteWithColorRelation(
        note = toEntity(colorEntity), color = colorEntity
    )
}