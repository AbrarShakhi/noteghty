package com.github.abrarshakhi.noteghty.note.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.github.abrarshakhi.noteghty.note.data.local.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Transaction
    @Query("SELECT * FROM notes")
    fun getNotes(): Flow<List<NoteEntity>>

    @Transaction
    @Query("SELECT * FROM notes as n WHERE n.id = :noteId")
    suspend fun getNotesById(noteId: Long): NoteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity): Long
}