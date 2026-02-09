package com.github.abrarshakhi.noteghty.note.data.local.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.github.abrarshakhi.noteghty.note.data.local.database.entity.NoteColorEntity
import com.github.abrarshakhi.noteghty.note.data.local.database.entity.NoteEntity

data class NoteWithColorRelation(
    @Embedded val note: NoteEntity,

    @Relation(
        parentColumn = "colorId", entityColumn = "id"
    ) val color: NoteColorEntity
)
