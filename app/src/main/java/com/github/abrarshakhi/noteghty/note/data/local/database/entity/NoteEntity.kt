package com.github.abrarshakhi.noteghty.note.data.local.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "notes", foreignKeys = [ForeignKey(
        entity = NoteColorEntity::class,
        parentColumns = ["id"],
        childColumns = ["colorId"],
        onDelete = ForeignKey.RESTRICT,
        onUpdate = ForeignKey.CASCADE
    )], indices = [Index(value = ["colorId"])]
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,

    val title: String,
    val content: String,

    val colorId: Long,

    val isPinned: Boolean,
    val isDeleted: Boolean,
    val updatedAt: Long,
)