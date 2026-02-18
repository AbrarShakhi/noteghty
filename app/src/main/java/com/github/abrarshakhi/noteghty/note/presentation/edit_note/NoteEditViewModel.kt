package com.github.abrarshakhi.noteghty.note.presentation.edit_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.abrarshakhi.noteghty.note.domain.model.NoteColor
import com.github.abrarshakhi.noteghty.note.domain.use_case.NoteEditUseCases
import com.github.abrarshakhi.outcome.onErr
import com.github.abrarshakhi.outcome.onOk
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@OptIn(FlowPreview::class, DelicateCoroutinesApi::class)
@HiltViewModel
class NoteEditViewModel @Inject constructor(
    private val useCases: NoteEditUseCases,
) : ViewModel() {

    private val _effect = MutableSharedFlow<NoteEditEffect>()
    val effect = _effect.asSharedFlow()

    private val _state = MutableStateFlow(NoteEditState(color = NoteColor.listOfColors.random()))
    val state = _state.asStateFlow()

    private inline fun update(reducer: NoteEditState.() -> NoteEditState) {
        val current = state.value
        val updated = current.reducer()
        if (current != updated) {
            _state.update { updated }
        }
    }

    private val saveMutex = Mutex()

    init {
        startAutoSave()
    }

    private fun startAutoSave() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                saveMutex.withLock {
                    useCases.saveNoteUseCase.sync(state.value.toNote()).onOk { noteId ->
                        update { copy(id = noteId) }
                    }
                }
            }
        }
    }

    fun onIntent(intent: NoteEditIntent) {
        when (intent) {
            is NoteEditIntent.Load -> loadNote(intent.noteId)
            is NoteEditIntent.Set -> setNewState(intent)
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

    private fun setNewState(changeIntent: NoteEditIntent.Set) {
        when (changeIntent) {
            is NoteEditIntent.Set.Content -> update { copy(content = changeIntent.newContent) }

            is NoteEditIntent.Set.Title -> update { copy(title = changeIntent.newTitle) }

            is NoteEditIntent.Set.TogglePinned -> update { togglePinned() }

            is NoteEditIntent.Set.Color -> setColorFromId(changeIntent.colorId)
        }
    }

    private fun setColorFromId(colorId: Long) {
        state.value.listOfColors.find { it.id == colorId }?.let { color ->
            update { copy(color = color) }
        }
    }

    private fun saveAsynchronously() {
        viewModelScope.launch { useCases.saveNoteUseCase.async(state.value.toNote()) }
    }
}