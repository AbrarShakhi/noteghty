package com.github.abrarshakhi.noteghty.core.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
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
}