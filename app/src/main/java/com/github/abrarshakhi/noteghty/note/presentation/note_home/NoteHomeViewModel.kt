package com.github.abrarshakhi.noteghty.note.presentation.note_home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.abrarshakhi.noteghty.core.presentation.state.UiState
import com.github.abrarshakhi.noteghty.note.data.local.preference.getNoteViewStyle
import com.github.abrarshakhi.noteghty.note.data.local.preference.setNoteViewStyle
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.model.NoteViewStyle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias NotesListState = UiState<List<Note>>

@HiltViewModel
class NoteHomeViewModel @Inject constructor(
    private val prefs: SharedPreferences
) : ViewModel() {

    private val _noteViewStyle = MutableStateFlow(NoteViewStyle.COZY)
    val viewStyle = _noteViewStyle.asStateFlow()
    fun toggleViewStyle() {
        val newStyle = when (_noteViewStyle.value) {
            NoteViewStyle.AGENDA -> NoteViewStyle.COZY
            NoteViewStyle.COZY -> NoteViewStyle.AGENDA
        }
        _noteViewStyle.update { newStyle }
        prefs.setNoteViewStyle(newStyle)
    }

    private val _notesState = MutableStateFlow<NotesListState>(NotesListState.loading(emptyList()))
    val notesState = _notesState.asStateFlow()

    init {
        _noteViewStyle.update { prefs.getNoteViewStyle() }
        loadNotes()
    }

    private fun loadNotes() {
        _notesState.update { it.asLoading() }
        viewModelScope.launch {
            _notesState.update {
//                it.asSuccess(emptyList())
                // TODO: Later need to fetch from repository
                it.asError("In development")
            }
        }
    }
}