package com.github.abrarshakhi.noteghty.note.data.local.preference

import android.content.SharedPreferences
import androidx.core.content.edit
import com.github.abrarshakhi.noteghty.note.domain.model.NoteViewStyle

private const val KEY_VIEW_STYLE = "key_view_style"

fun SharedPreferences.getNoteViewStyle(): NoteViewStyle {
    return when (getString(KEY_VIEW_STYLE, NoteViewStyle.COZY.name)) {
        NoteViewStyle.AGENDA.name -> NoteViewStyle.AGENDA
        else -> NoteViewStyle.COZY
    }
}

fun SharedPreferences.setNoteViewStyle(style: NoteViewStyle) {
    this.edit {
        putString(KEY_VIEW_STYLE, style.name)
    }
}
