package com.github.abrarshakhi.noteghty.note.data.local.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "notes", indices = [Index(value = ["colorId"])])
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,

    val title: String,
    val content: String,

    val colorId: Int,
//    val primaryColor: Int,
//    val backgroundColor: Int,
//    val isLightForeground: Boolean,

    val isPinned: Boolean,
    val isDeleted: Boolean,
    val updatedAt: Long,
)