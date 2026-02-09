package com.github.abrarshakhi.noteghty.note.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.github.abrarshakhi.noteghty.note.data.local.database.entity.NoteColorEntity
import com.github.abrarshakhi.noteghty.note.data.local.database.entity.NoteEntity
import com.github.abrarshakhi.noteghty.note.data.local.database.relation.NoteWithColorRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Transaction
    @Query("SELECT * FROM notes")
    fun getNotesWithColors(): Flow<List<NoteWithColorRelation>>

    @Transaction
    @Query("SELECT * FROM notes as n WHERE n.id = :noteId")
    suspend fun getNotesWithColorsById(noteId: Long): NoteWithColorRelation

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNoteColors(noteColorEntities: List<NoteColorEntity>)

    @Query("DELETE FROM note_colors")
    suspend fun clearAllNoteColors()

    @Query("SELECT * FROM note_colors")
    fun getNoteColors(): Flow<List<NoteColorEntity>>
}