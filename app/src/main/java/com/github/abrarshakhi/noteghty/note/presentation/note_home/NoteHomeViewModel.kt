package com.github.abrarshakhi.noteghty.note.presentation.note_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteHomeViewModel @Inject constructor(): ViewModel() {
    private val _notesState = MutableStateFlow<NotesListState>(NotesListState.loading(emptyList()))
    val notesState = _notesState.asStateFlow()

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            val notes = (1..100).map {
                Note(it, it.toString())
            }
            _notesState.update { it.asSuccess(notes) }
        }
    }
}