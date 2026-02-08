package com.github.abrarshakhi.noteghty.note.presentation.note_home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.abrarshakhi.noteghty.note.data.local.preference.setNoteListingOrder
import com.github.abrarshakhi.noteghty.note.data.local.preference.setNoteViewStyle
import com.github.abrarshakhi.noteghty.note.domain.listings.NoteOrder
import com.github.abrarshakhi.noteghty.note.domain.use_case.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteHomeViewModel @Inject constructor(
    private val prefs: SharedPreferences, private val getNotesUseCase: GetNotesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NoteHomeState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<NoteHomeEffect>()
    val effect = _effect.asSharedFlow()

    fun onIntent(noteHomeIntent: NoteHomeIntent) {
        when (noteHomeIntent) {
            is NoteHomeIntent.ToggleViewStyle -> toggleViewStyle()
            is NoteHomeIntent.SetNoteOrderingSettings -> setNoteOrderingSettings(noteHomeIntent.noteOrder)
            is NoteHomeIntent.LoadNotes -> loadNotes()
        }
    }

    private fun loadNotes() {
        viewModelScope.launch {
            getNotesUseCase(state.value.noteOrder).onStart { _state.update { it.startLoading() } }
                .catch { e ->
                    _effect.emit(
                        NoteHomeEffect.Error(
                            e.message ?: "Something went wrong"
                        )
                    )
                }.collect { notes -> _state.update { it.setNotes(notes = notes) } }
        }
    }

    private fun toggleViewStyle() {
        _state.update {
            val newStyle = it.inverseViewStyle()
            prefs.setNoteViewStyle(newStyle.viewStyle)
            return@update newStyle
        }
    }

    private fun setNoteOrderingSettings(noteOrder: NoteOrder) {
        _state.update { it.setOrder(noteOrder) }
        prefs.setNoteListingOrder(noteOrder)
    }
}