package com.github.abrarshakhi.noteghty.note.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.abrarshakhi.noteghty.note.data.local.database.dao.NoteDao
import com.github.abrarshakhi.noteghty.note.data.local.database.entity.NoteEntity

@Database(
    entities = [NoteEntity::class], version = 1, exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(NoteDatabase) {
                val instance =
                    Room.databaseBuilder(context, NoteDatabase::class.java, "note_db").build()
                INSTANCE = instance
                return@synchronized instance
            }
        }
    }
}
