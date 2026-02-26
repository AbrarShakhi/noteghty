package com.github.abrarshakhi.noteghty.core.di

import android.content.Context
import android.content.SharedPreferences
import com.github.abrarshakhi.noteghty.note.data.local.database.NoteDatabase
import com.github.abrarshakhi.noteghty.note.data.local.database.dao.NoteDao
import com.github.abrarshakhi.noteghty.note.data.repository.NoteRepositoryImpl
import com.github.abrarshakhi.noteghty.note.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Module
    @InstallIn(SingletonComponent::class)
    object CoroutineScopeModule {

        @Qualifier
        @Retention(AnnotationRetention.BINARY)
        annotation class ApplicationScope

        @Provides
        @Singleton
        @ApplicationScope
        fun provideApplicationCoroutineScope(): CoroutineScope =
            CoroutineScope(SupervisorJob() + Dispatchers.IO)

    }

    @Module
    @InstallIn(SingletonComponent::class)
    object PreferencesModule {

        @Provides
        @Singleton
        fun provideSharedPreferences(
            @ApplicationContext context: Context
        ): SharedPreferences = context.getSharedPreferences(
            "note_prefs", Context.MODE_PRIVATE
        )
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object RepositoryModule {
        @Provides
        @Singleton
        fun provideNoteRepository(
            noteDao: NoteDao,
            @CoroutineScopeModule.ApplicationScope applicationScope: CoroutineScope
        ): NoteRepository {
            return NoteRepositoryImpl(noteDao, applicationScope)
        }
    }


    @Module
    @InstallIn(SingletonComponent::class)
    object DatabaseModule {

        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context
        ): NoteDatabase = NoteDatabase.getDatabase(context)

        @Provides
        fun provideNoteDao(
            database: NoteDatabase
        ): NoteDao = database.noteDao()
    }
}