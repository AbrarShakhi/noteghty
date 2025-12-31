package com.github.abrarshakhi.noteghty.note.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.abrarshakhi.noteghty.note.data.data_source.local.dto.NoteDao
import com.github.abrarshakhi.noteghty.note.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "noteghty::note::db"
    }
}