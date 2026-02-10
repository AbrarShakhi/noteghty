package com.github.abrarshakhi.noteghty.note.presentation.edit_note

import androidx.compose.runtime.Stable
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.model.NoteColor

@Stable
data class NoteEditState(
    val isLoading: Boolean = true,
    val id: Long? = null,
    val title: String = "",
    val content: String = "",

    val color: NoteColor = NoteColor.listOfColors.random(),
    val isPinned: Boolean = false,
    val isDeleted: Boolean = false,
) {
    fun startLoading(): NoteEditState = if (!isLoading) copy(isLoading = true) else this

    fun stopLoading(): NoteEditState = if (isLoading) copy(isLoading = false) else this

    fun togglePinned(): NoteEditState = copy(isPinned = !isPinned)

    fun fromNote(note: Note): NoteEditState {
        return copy(
            isLoading = false,
            id = note.id,
            title = note.title,
            content = note.content,
            color = note.color,
            isPinned = note.isPinned,
            isDeleted = note.isDeleted,
        )
    }

    fun toNote(): Note {
        return Note.newInstance(
            id = id,
            title = title,
            content = content,
            color = color,
            isPinned = isPinned,
            isDeleted = isDeleted
        )
    }
}