package com.github.abrarshakhi.noteghty.note.presentation.edit_note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.abrarshakhi.noteghty.core.domain.utils.Outcome
import com.github.abrarshakhi.noteghty.core.domain.utils.onErr
import com.github.abrarshakhi.noteghty.core.domain.utils.onOk
import com.github.abrarshakhi.noteghty.note.domain.use_case.GetNoteByIdUseCase
import com.github.abrarshakhi.noteghty.note.domain.use_case.NoteEditUseCases
import com.github.abrarshakhi.noteghty.note.domain.use_case.SaveNoteUseCase
import com.github.abrarshakhi.noteghty.note.domain.utils.NoteError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, DelicateCoroutinesApi::class)
@HiltViewModel
class NoteEditViewModel @Inject constructor(
    private val noteEditUseCases: NoteEditUseCases,
    val savedStateHandle: SavedStateHandle, // TODO: Deal with this later.
) : ViewModel() {

    private val STATE_KEY = "note_editor_state"

    private val _effect = MutableSharedFlow<NoteEditEffect>()
    val effect = _effect.asSharedFlow()

    private val _state = MutableStateFlow(NoteEditState())
    val state = _state.asStateFlow()

    private inline fun update(reducer: NoteEditState.() -> NoteEditState) {
        val current = state.value
        val updated = current.reducer()
        if (current != updated) {
            _state.update { updated }
            savedStateHandle[STATE_KEY] = updated
        }
    }


    fun onIntent(intent: NoteEditIntent) {
        when (intent) {
            is NoteEditIntent.Load -> {}
            is NoteEditIntent.TitleChanged -> {}
            is NoteEditIntent.BodyChanged -> {}
            is NoteEditIntent.ColorChanged -> {}
            is NoteEditIntent.PinToggled -> {}
            is NoteEditIntent.SaveForcedAndNotify -> {}
        }
    }

    private fun loadNote(noteId: Long?) {
        if (noteId == null) {
            update { copy(isLoading = false) }
            return
        }

        viewModelScope.launch {
            noteEditUseCases.getNoteByIdUseCase(noteId).onErr { e -> _effect.emit(NoteEditEffect.Error("Not Not Found")) }
                .onOk { note ->
                    update { copy(isLoading = false) }
                }
        }
    }

    private fun saveOrPass() {
        if (state.value.isLoading) return

        viewModelScope.launch { saveState(state.value) }
    }

    private suspend fun saveState(state: NoteEditState): Outcome<Long, NoteError> =
        noteEditUseCases.saveNoteUseCase(state.note!!).onOk { newId -> update { copy(note = note?.copy(id = newId)) } }


    private fun saveForcedAndNotify() {
        viewModelScope.launch {
            saveState(state.value).onOk { newId ->
                _effect.emit(NoteEditEffect.SavedSuccessfulAndReadyToGoBack)
            }
        }
    }
}