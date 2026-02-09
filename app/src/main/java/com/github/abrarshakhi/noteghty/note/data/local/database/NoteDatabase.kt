package com.github.abrarshakhi.noteghty.note.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github.abrarshakhi.noteghty.note.data.local.database.dao.NoteDao
import com.github.abrarshakhi.noteghty.note.data.local.database.entity.NoteColorEntity
import com.github.abrarshakhi.noteghty.note.data.local.database.entity.NoteEntity
import com.github.abrarshakhi.noteghty.note.data.mapper.toEntity
import com.github.abrarshakhi.noteghty.note.domain.model.NoteColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [NoteEntity::class, NoteColorEntity::class], version = 1, exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(NoteDatabase) {
                val instance = Room.databaseBuilder(context, NoteDatabase::class.java, "note_db")
                    .addCallback(callback = object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            INSTANCE?.let { db ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    db.noteDao()
                                        .insertAllNoteColors(NoteColor.listOfColors.map { it.toEntity() })
                                }
                            }
                        }
                    }).build()
                INSTANCE = instance
                return@synchronized instance
            }
        }
    }
}
