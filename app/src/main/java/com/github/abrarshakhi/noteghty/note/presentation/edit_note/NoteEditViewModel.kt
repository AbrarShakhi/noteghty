package com.github.abrarshakhi.noteghty.note.presentation.edit_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.abrarshakhi.noteghty.core.domain.utils.onErr
import com.github.abrarshakhi.noteghty.core.domain.utils.onOk
import com.github.abrarshakhi.noteghty.note.domain.use_case.NoteEditUseCases
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
    private val useCases: NoteEditUseCases,
) : ViewModel() {

    private val _effect = MutableSharedFlow<NoteEditEffect>()
    val effect = _effect.asSharedFlow()

    private val _state = MutableStateFlow(NoteEditState())
    val state = _state.asStateFlow()

    private inline fun update(reducer: NoteEditState.() -> NoteEditState) {
        val current = state.value
        val updated = current.reducer()
        if (current != updated) {
            _state.update { updated }
        }
    }

    fun onIntent(intent: NoteEditIntent) {
        when (intent) {
            is NoteEditIntent.Load -> loadNote(intent.noteId)
            is NoteEditIntent.TogglePinned -> update { togglePinned() }
            is NoteEditIntent.ChangeTitleOrContent -> onChangeTitleOrContent(intent)
            is NoteEditIntent.SaveAsynchronous -> saveAsynchronously()
        }
    }

    private fun loadNote(noteId: Long?) {
        if (noteId == null) {
            update { stopLoading() }
            return
        }
        update { startLoading() }
        viewModelScope.launch {
            useCases.getNoteByIdUseCase(noteId).onOk { note ->
                update { fromNote(note) }
            }.onErr { e ->
                _effect.emit(NoteEditEffect.Error("Not Not Found"))
                update { stopLoading() }
            }
        }
    }

    private fun onChangeTitleOrContent(changeIntent: NoteEditIntent.ChangeTitleOrContent) {
        when (changeIntent) {
            is NoteEditIntent.ChangeTitleOrContent.Content -> update {
                copy(content = changeIntent.newContent)
            }

            is NoteEditIntent.ChangeTitleOrContent.Title -> update {
                copy(title = changeIntent.newTitle)
            }
        }
    }

    private fun saveAsynchronously() {
        viewModelScope.launch { useCases.saveNoteUseCase.async(state.value.toNote()) }
    }
}