package com.github.abrarshakhi.noteghty.note.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "note_colors")
data class NoteColorEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,

    val primaryColor: Long, val backgroundColor: Long, val foregroundColor: Long
)