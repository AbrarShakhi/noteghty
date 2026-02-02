package com.github.abrarshakhi.noteghty.note.data.repository

import com.github.abrarshakhi.noteghty.core.domain.utils.Outcome
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.model.NoteColor
import com.github.abrarshakhi.noteghty.note.domain.repository.NoteRepository
import com.github.abrarshakhi.noteghty.note.domain.utils.NoteError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NoteRepositoryImpl : NoteRepository {

    private val notes = mutableListOf(

        Note.create(
            title = "Daily Reflection", content = """
                Today was one of those days where nothing extraordinary happened,
                yet it still felt meaningful. I woke up earlier than usual, made
                some coffee, and sat quietly for a few minutes before checking my phone.

                I noticed how often I rush into the day without giving myself
                space to breathe. Slowing down even a little helped me focus better
                and feel less anxious.

                I want to make this a habit: wake up, breathe, reflect.
                Progress doesn’t always need to be loud or visible.
            """.trimIndent(), color = NoteColor.random()
        ),

        Note.create(
            title = "Project Ideas", content = """
                1. Notes app with offline-first architecture
                   - Room database
                   - Flow + Compose
                   - Clean Architecture

                2. Habit tracker
                   - Daily streaks
                   - Simple analytics
                   - Minimal UI

                3. Personal knowledge base
                   - Markdown support
                   - Tag-based search
                   - Cross-device sync (future)

                Focus on finishing ONE project properly instead of starting many.
                Done is better than perfect.
            """.trimIndent(), color = NoteColor.random(), isPinned = true
        ),

        Note.create(
            title = "Thoughts on Learning", content = """
                Learning isn’t about consuming endless tutorials.
                It’s about struggling, failing, and slowly understanding why
                something works the way it does.

                When I write code myself, even if it’s ugly,
                I remember it far better than when I just watch someone else do it.

                The confusion is part of the process.
                The frustration means growth is happening.
                I should stop avoiding it.
            """.trimIndent(), color = NoteColor.random()
        ),

        Note.create(
            title = "Long-Term Goals", content = """
                • Become confident in Android development
                • Build apps that people actually enjoy using
                • Write clean, understandable code
                • Learn system design fundamentals
                • Improve communication skills

                These goals won’t be achieved overnight.
                Small daily progress matters more than motivation.
                Consistency beats intensity.
            """.trimIndent(), color = NoteColor.random()
        ),

        Note.create(
            title = "Random Brain Dump", content = """
                Why do good ideas always come when I’m about to sleep?
                I should keep a notebook near my bed.

                Dark mode UIs feel calmer.
                Pastel colors feel friendly.
                Simple apps age better than complex ones.

                Remember: users don’t care about architecture,
                they care about how the app makes them feel.
            """.trimIndent(), color = NoteColor.random()
        )
    )

    override fun getNotes(): Flow<List<Note>> = flow {
        emit(notes)
    }

    override fun getNoteById(noteId: Int): Outcome<Note, NoteError> {
        notes[noteId] = notes[noteId].copy(id = noteId)
        return Outcome.ok(notes[noteId])
    }

    override fun saveNote(note: Note): Outcome<Unit, NoteError> {
        notes.add(note)
        return Outcome.ok(Unit)
    }
}
