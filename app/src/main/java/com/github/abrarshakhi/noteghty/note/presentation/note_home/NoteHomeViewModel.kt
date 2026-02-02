package com.github.abrarshakhi.noteghty.note.presentation.note_home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.abrarshakhi.noteghty.core.domain.listings.ListingOrder
import com.github.abrarshakhi.noteghty.core.presentation.state.UiState
import com.github.abrarshakhi.noteghty.note.data.local.preference.getNoteViewStyle
import com.github.abrarshakhi.noteghty.note.data.local.preference.setNoteViewStyle
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.model.NoteViewStyle
import com.github.abrarshakhi.noteghty.note.domain.use_case.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias NotesListState = UiState<List<Note>>

@HiltViewModel
class NoteHomeViewModel @Inject constructor(
    private val prefs: SharedPreferences, private val getNotesUseCase: GetNotesUseCase
) : ViewModel() {

    private val _noteViewStyle = MutableStateFlow(NoteViewStyle.COZY)
    val viewStyle = _noteViewStyle.asStateFlow()

    private val _notesState = MutableStateFlow<NotesListState>(NotesListState.loading(emptyList()))
    val notesState = _notesState.asStateFlow()

    init {
        _noteViewStyle.update { prefs.getNoteViewStyle() }
        loadNotes()
    }

    fun loadNotes() {
        viewModelScope.launch {
            getNotesUseCase(GetNotesUseCase.NoteOrder.Date(ListingOrder.Ascending)).onStart { _notesState.update { it.asLoading() } }
                .catch { e -> _notesState.update { it.asError(e.message ?: "Unknown Error") } }
                .collect { notes -> _notesState.update { it.asSuccess(notes) } }
        }
    }

    fun toggleViewStyle() {
        val newStyle = when (_noteViewStyle.value) {
            NoteViewStyle.AGENDA -> NoteViewStyle.COZY
            NoteViewStyle.COZY -> NoteViewStyle.AGENDA
        }
        _noteViewStyle.update { newStyle }
        prefs.setNoteViewStyle(newStyle)
    }
}